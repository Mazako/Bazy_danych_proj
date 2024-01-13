package pl.tourpol.backend.api.tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.tourpol.backend.persistance.IncomingTour;
import pl.tourpol.backend.persistance.entity.Facility;
import pl.tourpol.backend.persistance.repository.RoomContractRepository;
import pl.tourpol.backend.persistance.repository.RoomTourRepository;
import pl.tourpol.backend.persistance.repository.TourRepository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class TourService {
    private final TourRepository tourRepository;
    private final RoomTourRepository roomTourRepository;
    private final RoomContractRepository roomContractRepository;

    public TourService(TourRepository tourRepository, RoomTourRepository roomTourRepository, RoomContractRepository roomContractRepository) {
        this.tourRepository = requireNonNull(tourRepository);
        this.roomTourRepository = requireNonNull(roomTourRepository);
        this.roomContractRepository = requireNonNull(roomContractRepository);
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
                incomingTour.getName(),
                incomingTour.getDescription(),
                incomingTour.getPrice(),
                incomingTour.getDepartureDate(),
                incomingTour.getReturnDate(),
                getTotalRoomsCapacity(incomingTour.getId()),
                calculateAvailablePlacesForTour(incomingTour.getId()));
    }


}
