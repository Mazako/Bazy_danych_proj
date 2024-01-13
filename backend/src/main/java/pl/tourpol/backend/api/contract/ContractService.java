package pl.tourpol.backend.api.contract;


import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.api.room.RoomDTO;
import pl.tourpol.backend.api.tour.TourService;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Contract;
import pl.tourpol.backend.persistance.entity.RoomContract;
import pl.tourpol.backend.persistance.repository.ContractRepository;
import pl.tourpol.backend.persistance.repository.RoomContractRepository;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;
import pl.tourpol.backend.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ContractService {

    private final ContractRepository contractRepository;
    private final RoomContractRepository roomContractRepository;
    private final UserService userService;
    private final TourService tourService;

    public ContractService(ContractRepository contractRepository,
                           RoomContractRepository roomContractRepository,
                           UserService userService, TourService tourService) {
        this.contractRepository = requireNonNull(contractRepository);
        this.roomContractRepository = requireNonNull(roomContractRepository);
        this.userService = requireNonNull(userService);
        this.tourService = requireNonNull(tourService);
    }

    public List<ContractDTO> getAllContracts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = user.getUsername();
        AppUser appUser = userService.getUserByEmail(mail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));
        List<Contract> contracts = contractRepository.findAllByUserId(appUser.getId());
        return contracts.stream()
                .map(this::convertToContractDTO)
                .toList();
    }

    @Transactional
    public ContractDTO addContract(long tourId, List<Long> roomIds) {
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

        return convertToContractDTO(contractRepository.save(contract));
    }

    private float getTotalprice(Contract contract) {
        float price = contract.getTour().getPrice();
        return roomContractRepository.findRoomContractByContractId(contract.getId())
                .stream()
                .map(roomContract -> roomContract.getRoom().getPersonCount())
                .map(count -> BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(count)))
                .reduce(BigDecimal::add)
                .map(BigDecimal::floatValue)
                .orElse(0f);
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
