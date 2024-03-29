package pl.tourpol.backend.api.contract;


import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.api.contract.listener.ContractCreationEvent;
import pl.tourpol.backend.api.contract.listener.ContractRefundEvent;
import pl.tourpol.backend.api.notification.NotificationService;
import pl.tourpol.backend.api.notification.NotificationTypeValue;
import pl.tourpol.backend.api.room.RoomDTO;
import pl.tourpol.backend.api.tour.TourService;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Contract;
import pl.tourpol.backend.persistance.repository.ContractRepository;
import pl.tourpol.backend.persistance.repository.RoomContractRepository;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperationType;
import pl.tourpol.backend.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class ContractService {

    private final ContractRepository contractRepository;
    private final RoomContractRepository roomContractRepository;
    private final UserService userService;
    private final TourService tourService;
    private final NotificationService notificationService;
    private final ApplicationEventPublisher eventPublisher;
    private final AccessSensitiveOperation accessSensitiveOperation;

    public ContractService(ContractRepository contractRepository,
                           RoomContractRepository roomContractRepository,
                           UserService userService, TourService tourService,
                           NotificationService notificationService,
                           ApplicationEventPublisher eventPublisher,
                           AccessSensitiveOperation accessSensitiveOperation) {
        this.contractRepository = requireNonNull(contractRepository);
        this.roomContractRepository = requireNonNull(roomContractRepository);
        this.userService = requireNonNull(userService);
        this.tourService = requireNonNull(tourService);
        this.notificationService = requireNonNull(notificationService);
        this.eventPublisher = requireNonNull(eventPublisher);
        this.accessSensitiveOperation = accessSensitiveOperation;
    }

    public Page<ContractDTO> getAllContracts(int page, String statuses) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = user.getUsername();
        AppUser appUser = userService.getUserByEmail(mail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));
        List<String> enumStatuses = Arrays.stream(statuses.split(","))
                .collect(toList());
        Page<Contract> contracts = contractRepository.findAllByUserIdAndStatuses(appUser.getId(), enumStatuses, PageRequest.of(page, 10));
        return contracts.map(this::convertToContractDTO);

    }

    @Transactional
    public ContractDTO addContract(long tourId, List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) {
            throw new RequestException(RequestErrorMessage.EMPTY_LIST);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = user.getUsername();
        AppUser appUser = userService.getUserByEmail(mail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        var tour = tourService.findTour(tourId).orElseThrow(() -> new RequestException(RequestErrorMessage.TOUR_NOT_EXISTS));
        if (tourService.getAvailableRooms(tourId).isEmpty()) {
            throw new RequestException(RequestErrorMessage.ROOM_BUSY);
        }

        var contract = contractRepository.save(new Contract(LocalDate.now(), (short) 1, appUser, tour, Contract.Status.PENDING_PAYMENT));

        short count = roomIds.stream()
                .map(id -> tourService.addRoomToContract(id, contract.getId()))
                .map(roomContract -> roomContract.getRoom().getPersonCount())
                .reduce((short) 0, (total, e) -> (short) (total + e));

        contract.setPearsonCount(count);
        notificationService.sendNotification(contract.getId(),
                "Pomyślnie utworzono rezerwację do: " + tour.getName() + "\nNa adres mail wysłano potwierdzenie wraz z numerem konta, na które należy dokonać płatność",
                NotificationTypeValue.RESERVATION_CONFIRM);


        ContractDTO contractDTO = convertToContractDTO(contractRepository.save(contract));
        eventPublisher.publishEvent(new ContractCreationEvent(appUser.getMail(), appUser.getName(), contractDTO.totalPrice, tour.getResort().getName()));
        return contractDTO;
    }

    public float checkRefundPossibility(long contractId) {
        return accessSensitiveOperation.callWithAccessCheck(contractId,
                contractRepository::getRefundFactor,
                AccessSensitiveOperationType.CONTRACT_ACCESS);
    }

    @Transactional
    public float refundContract(long contractId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = user.getUsername();
        AppUser appUser = userService.getUserByEmail(mail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        var contract = contractRepository.findById(contractId).orElseThrow(() -> new RequestException(RequestErrorMessage.CONTRACT_NOT_EXISTS));

        if (!appUser.equals(contract.getUser())) {
            throw new AccessDeniedException("Cannot operate on this contract");
        }

        float refundFactor = contractRepository.getRefundFactor(contractId);
        if (refundFactor == 0.0f) {
            throw new RequestException(RequestErrorMessage.REFUND_NOT_POSSIBLE);
        }
        float refundValue = BigDecimal.valueOf(refundFactor).multiply(BigDecimal.valueOf(getTotalprice(contract))).floatValue();

        eventPublisher.publishEvent(new ContractRefundEvent(appUser.getMail(), appUser.getName(), refundValue, contract.getTour().getResort().getName()));

        contractRepository.delete(contract);
        return refundValue;
    }

    private float getTotalprice(Contract contract) {
        float price = contract.getTour().getPrice();
        short pearsonCount = contract.getPearsonCount();
        return BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(pearsonCount)).floatValue();
//        return roomContractRepository.findRoomContractByContractId(contract.getId())
//                .stream()
//                .map(roomContract -> roomContract.getRoom().getPersonCount())
//                .map(count -> BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(count)))
//                .reduce(BigDecimal::add)
//                .map(BigDecimal::floatValue)
//                .orElse(0f);
    }

    public ContractDTO convertToContractDTO(Contract contract) {

        List<RoomDTO> roomDTOs = roomContractRepository.findRoomContractByContractId(contract.getId()).stream()
                .map(RoomDTO::toDto)
                .toList();
        return new ContractDTO(
                contract.getId(),
                contract.getTour().getResort().getName(),
                contract.getTour().getDepartureDate(),
                contract.getTour().getReturnDate(),
                contract.getTour().getResort().getAddress().getCity().getCountry(),
                contract.getTour().getResort().getAddress().getCity().getName(),
                contract.getReservationDate(),
                contract.getStatus().toString(),
                contract.getPearsonCount(),
                getTotalprice(contract),
                roomDTOs
        );
    }

    public record ContractDTO(
            long id,
            String resortName,
            LocalDate departureDate,
            LocalDate returnDate,
            String country,
            String city,
            LocalDate reservationDate,
            String status,
            Short personCount,
            float totalPrice,
            List<RoomDTO> rooms
    ) {
    }

}
