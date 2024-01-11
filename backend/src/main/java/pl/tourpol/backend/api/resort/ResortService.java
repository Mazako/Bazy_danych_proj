package pl.tourpol.backend.api.resort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.tourpol.backend.api.resort.ResortController.SearchRequestDTO;
import pl.tourpol.backend.persistance.entity.Address;
import pl.tourpol.backend.persistance.entity.Facility;
import pl.tourpol.backend.persistance.entity.Resort;
import pl.tourpol.backend.persistance.entity.Tour;
import pl.tourpol.backend.persistance.repository.ResortRepository;
import pl.tourpol.backend.persistance.repository.RoomContractRepository;
import pl.tourpol.backend.persistance.repository.RoomTourRepository;
import pl.tourpol.backend.persistance.repository.TourRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ResortService {

    private final ResortRepository resortRepository;
    private final TourRepository tourRepository;
    private final RoomTourRepository roomTourRepository;
    private final RoomContractRepository roomContractRepository;

    public ResortService(ResortRepository resortRepository, TourRepository tourRepository, RoomTourRepository roomTourRepository, RoomContractRepository roomContractRepository) {
        this.resortRepository = requireNonNull(resortRepository);
        this.tourRepository = requireNonNull(tourRepository);
        this.roomTourRepository = requireNonNull(roomTourRepository);
        this.roomContractRepository = requireNonNull(roomContractRepository);
    }

    public ResortDTO getResortById(Long id) {
        Resort resort = resortRepository.findById(id).orElse(null);
        if (resort == null) {
            return null;
        }
        List<Tour> tours = tourRepository.findToursByResortId(id);
        return convertToResortDTO(resort, tours);
    }

    public Page<ResortsList> getAllResort(int page) {
        Page<Resort> allResorts = resortRepository.findAllResorts(PageRequest.of(page, 15));
        return allResorts.map(this::convertToResortsList);
    }

    private ResortsList convertToResortsList(Resort resort) {
        LocalDate today = LocalDate.now();
        Optional<Tour> nearestTourOpt = tourRepository.findNearestUpcomingTourForResort(resort.getId(), today);
        LocalDate departureDate = null;
        LocalDate returnDate = null;
        Float price = null;
        float averageOpinion = Optional.ofNullable(resort.getAvgOpinion()).orElse(0.0f);

        if (nearestTourOpt.isPresent()) {
            Tour nearestTour = nearestTourOpt.get();
            departureDate = nearestTour.getDepartureDate();
            returnDate = nearestTour.getReturnDate();
            price = nearestTour.getPrice();
        }
        return new ResortsList(
                resort.getName(),
                averageOpinion,
                resort.getAddress().getCity().getCountry(),
                resort.getAddress().getCity().getName(),
                resort.getAddress().getCity().getLatitude(),
                resort.getAddress().getCity().getLongitude(),
                price,
                departureDate,
                returnDate
        );
    }

    private ResortDTO convertToResortDTO(Resort resort, List<Tour> tours) {
        List<TourDTO> tourDTOs = tours.stream()
                .map(this::convertToTourDTO)
                .toList();

        return new ResortDTO(
                resort.getName(),
                resort.getAddress().getCity().getCountry(),
                resort.getAddress().getCity().getName(),
                resort.getAddress(),
                tourDTOs
        );
    }

    private TourDTO convertToTourDTO(Tour tour) {
        return new TourDTO(
                tour.getFacility(),
                tour.getName(),
                tour.getPrice(),
                tour.getDepartureDate(),
                tour.getReturnDate(),
                getTotalRoomCapacity(tour.getId()),
                calculateAvailablePlacesForTour(tour.getId())
        );
    }

    public int calculateAvailablePlacesForTour(Long tourId) {
        int totalConfirmedPersons = getTotalConfirmedPersons(tourId);
        int totalRoomCapacity = getTotalRoomCapacity(tourId);
        return totalRoomCapacity - totalConfirmedPersons;
    }

    public Short getTotalRoomCapacity(Long tourId) {
        return Optional.ofNullable(roomTourRepository.sumTotalCapacityForTour(tourId)).orElse((short) 0);
    }

    public Short getTotalConfirmedPersons(Long tourId) {
        return Optional.ofNullable(roomContractRepository.sumConfirmedPearsonCountForTour(tourId)).orElse((short) 0);
    }

    public Page<ResortsList> searchResorts(SearchRequestDTO searchParams) {
        Page<Resort> filtredResorts = resortRepository.findResortWithFilters(searchParams.resortName(),
                searchParams.city(),
                searchParams.country(),
                searchParams.minPrice(),
                searchParams.maxPrice(),
                searchParams.departureDate(),
                searchParams.returnDate(),
                PageRequest.of(searchParams.page(), 15));
        return filtredResorts.map(this::convertToResortsList);
    }

    public record ResortsList(
            String resortName,
            float averageOpinion,
            String country,
            String city,
            String latitude,
            String longitude,
            Float price,
            LocalDate departureData,
            LocalDate returnDate
    ) {
    }

    public record TourDTO(
            Facility facilities,
            String name,
            Float price,
            LocalDate departureDate,
            LocalDate returnDate,
            Short roomsCount,
            Integer placesLeft
    ) {
    }

    public record ResortDTO(
            String resortName,
            String country,
            String city,
            Address address,
            List<TourDTO> tours
    ) {
    }


}
