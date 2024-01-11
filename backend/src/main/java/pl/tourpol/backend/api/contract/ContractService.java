package pl.tourpol.backend.api.contract;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Contract;
import pl.tourpol.backend.persistance.entity.Room;
import pl.tourpol.backend.persistance.entity.RoomContract;
import pl.tourpol.backend.persistance.repository.ContractRepository;
import pl.tourpol.backend.persistance.repository.RoomContractRepository;
import pl.tourpol.backend.user.UserService;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ContractService {

    private final ContractRepository contractRepository;
    private final RoomContractRepository roomContractRepository;
    private final UserService userService;

    public ContractService(ContractRepository contractRepository,
                           RoomContractRepository roomContractRepository,
                           UserService userService) {
        this.contractRepository = requireNonNull(contractRepository);
        this.roomContractRepository = requireNonNull(roomContractRepository);
        this.userService = requireNonNull(userService);
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

    public ContractDTO convertToContractDTO(Contract contract) {

        List<RoomDTO> roomDTOs = roomContractRepository.findRoomContractByContractId(contract.getId()).stream()
                .map(this::convertToRoomDTO)
                .toList();
        return new ContractDTO(
                contract.getTour().getResort().getName(),
                contract.getTour().getDepartureDate(),
                contract.getTour().getReturnDate(),
                contract.getTour().getResort().getAddress().getCity().getCountry(),
                contract.getTour().getResort().getAddress().getCity().getName(),
                contract.getReservationDate(),
                contract.getStatus().toString(),
                contract.getPearsonCount(),
                roomDTOs
        );
    }

    public RoomDTO convertToRoomDTO(RoomContract roomContract) {
        Room room = roomContract.getRoom();
        return new RoomDTO(room.getName(), room.getPersonCount(), room.getStandard());
    }

    public record RoomDTO(String name, Short personCount, Short standard) {
    }

    public record ContractDTO(
            String resortName,
            LocalDate departureDate,
            LocalDate returnDate,
            String country,
            String city,
            LocalDate reservationDate,
            String status,
            Short personCount,
            List<RoomDTO> rooms
    ) {
    }

}
