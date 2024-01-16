package pl.tourpol.backend.api.tour;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.tourpol.backend.api.room.RoomService;
import pl.tourpol.backend.persistance.IncomingTour;
import pl.tourpol.backend.persistance.entity.*;
import pl.tourpol.backend.persistance.repository.*;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class TourService {
    private final TourRepository tourRepository;
    private final RoomTourRepository roomTourRepository;
    private final RoomContractRepository roomContractRepository;
    private final ResortRepository resortRepository;
    private final FacilityRepository facilityRepository;
    private final RoomService roomService;
    private final ContractRepository contractRepository;

    public TourService(TourRepository tourRepository, RoomTourRepository roomTourRepository, RoomContractRepository roomContractRepository, ResortRepository resortRepository, FacilityRepository facilityRepository, RoomService roomService, ContractRepository contractRepository) {
        this.tourRepository = requireNonNull(tourRepository);
        this.roomTourRepository = requireNonNull(roomTourRepository);
        this.roomContractRepository = requireNonNull(roomContractRepository);
        this.resortRepository = requireNonNull(resortRepository);
        this.facilityRepository = requireNonNull(facilityRepository);
        this.roomService = requireNonNull(roomService);
        this.contractRepository = requireNonNull(contractRepository);
    }

    public Page<TourDTO> getIncomingTours(SearchTourParams params) {
        return tourRepository.findIncomingTours(params.resortId(),
                        params.name(),
                        params.price(),
                        params.departureDate(),
                        params.returnDate(),
                        params.wiFi(),
                        params.swimmingPool(),
                        params.airConditioning(),
                        params.gym(),
                        params.food(),
                        params.roomService(),
                        params.bar(),
                        params.restaurant(),
                        params.freeParking(),
                        params.allTimeReception(),
                        PageRequest.of(params.page(), 10))
                .map(this::mapToDto);

    }

    @Transactional
    public Tour addTour(NewTourData data) {
        validateTourAdd(data.name(), data.description(), data.departureDate(), data.returnDate(), data.price());
        var resort = resortRepository.findById(data.resortId())
                .orElseThrow(() -> new RequestException(RequestErrorMessage.RESORT_NOT_EXISTS));

        var facility = addOrGetExistingFacility(data.facilities());
        long tourId = tourRepository.save(new Tour(data.name(), data.price(), data.departureDate(), data.returnDate(),
                data.description(), facility, resort)).getId();
        data.roomIds()
                .forEach(id -> addRoomToTour(id, tourId));

        return tourRepository.findById(tourId).get();
    }

    private void validateTourAdd(String name, String description, LocalDate departureDate, LocalDate returnDate, float price) {
        if (StringUtils.isBlank(name)) {
            throw new RequestException(RequestErrorMessage.INVALID_NAME);
        }
        if (StringUtils.isBlank(description)) {
            throw new RequestException(RequestErrorMessage.INVALID_DESCRIPTION);
        }
        if (departureDate == null || returnDate == null || departureDate.isAfter(returnDate)) {
            throw new RequestException(RequestErrorMessage.INVALID_DATE);
        }
        if (price <= 0) {
            throw new RequestException(RequestErrorMessage.INVALID_PRICE);
        }
    }

    public Facility addOrGetExistingFacility(FacilityDto facilityDto) {
        return facilityRepository.findFacility(facilityDto.wifi(),
                        facilityDto.swimmingPool(),
                        facilityDto.airConditioning(),
                        facilityDto.gym(),
                        facilityDto.food(),
                        facilityDto.roomService(),
                        facilityDto.bar(),
                        facilityDto.restaurant(),
                        facilityDto.freeParking(),
                        facilityDto.allTimeReception())
                .orElseGet(() -> facilityRepository.save(facilityDto.toEntity()));
    }

    @Transactional
    public RoomTour addRoomToTour(long roomId, long tourId) {
        var tour = tourRepository.findById(tourId).orElseThrow(() -> new RequestException(RequestErrorMessage.TOUR_NOT_EXISTS));
        Room room = roomService.getAvailableRoomById(roomId, tour.getDepartureDate(), tour.getReturnDate())
                .orElseThrow(() -> new RequestException(RequestErrorMessage.ROOM_BUSY));
        return roomTourRepository.save(new RoomTour(room, tour));
    }

    @Transactional
    public RoomContract addRoomToContract(long roomId, long contractId) {
        var room = roomService.getRoomById(roomId).orElseThrow(() -> new RequestException(RequestErrorMessage.ROOM_NOT_EXISTS));
        var contract = contractRepository.findById(contractId).orElseThrow(() -> new RequestException(RequestErrorMessage.CONTRACT_NOT_EXISTS));
        long tourId = contract.getTour().getId();

        boolean isAvailable = getAvailableRooms(tourId)
                .stream()
                .map(Room::getId)
                .anyMatch(id -> id == roomId);

        if (!isAvailable) {
            throw new RequestException(RequestErrorMessage.ROOM_BUSY);
        }

        return roomContractRepository.save(new RoomContract(room, contract));
    }

    public List<Room> getAvailableRooms(long tourId) {
        List<Long> roomsAssignedToTour = roomTourRepository.getRoomsAssignedToTour(tourId).stream()
                .map(Room::getId)
                .toList();

        List<Long> takenRooms = roomContractRepository.getRoomsAssignedToTour(tourId).stream()
                .map(Room::getId)
                .toList();

        List<Long> availableRooms = new ArrayList<>(roomsAssignedToTour);
        availableRooms.removeAll(takenRooms);

        return availableRooms
                .stream()
                .map(roomService::getRoomById)
                .map(Optional::get)
                .toList();
    }

    public Optional<Tour> findTour(long tourId) {
        return tourRepository.findById(tourId);
    }

    private short calculateAvailablePlacesForTour(long tourId) {
        return (short) (getTotalRoomsCapacity(tourId) - getTotalConfirmedPersons(tourId));
    }

    public short getTotalRoomsCapacity(long tourId) {
        return Optional.ofNullable(roomTourRepository.sumTotalCapacityForTour(tourId)).orElse((short) 0);
    }

    public short getTotalConfirmedPersons(long tourId) {
        return Optional.ofNullable(roomContractRepository.sumConfirmedPearsonCountForTour(tourId)).orElse((short) 0);
    }

    private TourDTO mapToDto(IncomingTour incomingTour) {
        return new TourDTO(new Facility(incomingTour.getWifi(),
                incomingTour.getSwimmingPool(),
                incomingTour.getAirConditioning(),
                incomingTour.getGym(),
                incomingTour.getFood(),
                incomingTour.getRoomService(),
                incomingTour.getBar(),
                incomingTour.getRestaurant(),
                incomingTour.getFreeParking(),
                incomingTour.getAllTimeReception()),
                incomingTour.getId(),
                incomingTour.getName(),
                incomingTour.getDescription(),
                incomingTour.getPrice(),
                incomingTour.getDepartureDate(),
                incomingTour.getReturnDate(),
                getTotalRoomsCapacity(incomingTour.getId()),
                calculateAvailablePlacesForTour(incomingTour.getId()));
    }


}
