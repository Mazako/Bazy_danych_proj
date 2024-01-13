package pl.tourpol.backend.api.resort;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.tourpol.backend.api.location.LocationService;
import pl.tourpol.backend.api.resort.ResortController.SearchRequestDTO;
import pl.tourpol.backend.api.room.RoomService;
import pl.tourpol.backend.persistance.PopularityEntry;
import pl.tourpol.backend.persistance.entity.Address;
import pl.tourpol.backend.persistance.entity.Resort;
import pl.tourpol.backend.persistance.entity.Tour;
import pl.tourpol.backend.persistance.repository.PopularityReportRepository;
import pl.tourpol.backend.persistance.repository.ResortRepository;
import pl.tourpol.backend.persistance.repository.TourRepository;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ResortService {

    private final ResortRepository resortRepository;
    private final TourRepository tourRepository;
    private final LocationService locationService;
    private final RoomService roomService;
    private final PopularityReportRepository popularityReportRepository;

    public ResortService(ResortRepository resortRepository, TourRepository tourRepository, LocationService locationService, RoomService roomService, PopularityReportRepository popularityReportRepository) {
        this.resortRepository = requireNonNull(resortRepository);
        this.tourRepository = requireNonNull(tourRepository);
        this.locationService = requireNonNull(locationService);
        this.roomService = requireNonNull(roomService);
        this.popularityReportRepository = popularityReportRepository;
    }

    public Page<ResortListItem> searchResorts(SearchRequestDTO searchParams) {
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

    public ResortDto getResortById(Long id) {
        Resort resort = resortRepository.findById(id).orElse(null);
        if (resort == null) {
            return null;
        }
        return convertToResortDTO(resort);
    }

    public Page<ResortListItem> getAllResort(int page) {
        Page<Resort> allResorts = resortRepository.findAllResorts(PageRequest.of(page, 15));
        return allResorts.map(this::convertToResortsList);
    }

    @Transactional
    public Resort addResort(NewResortData newResortData) {
        validate(newResortData.name(), newResortData.description());

        var addressDTO = newResortData.addressDTO();
        Address address = locationService.addLocation(addressDTO.street(), addressDTO.buildingNumber(), addressDTO.houseNumber(),
                addressDTO.city().name(), addressDTO.city().country(), addressDTO.city().longitude(), addressDTO.city().latitude());

        var resort = resortRepository.save(new Resort(newResortData.name(), address, newResortData.description()));

        newResortData.rooms()
                .forEach(room -> roomService.addRoom(resort.getId(), room.name(), room.personCount(), room.standard()));

        return resort;
    }

    public List<PopularityEntry> generatePopularityReport(LocalDate startDate, LocalDate endDate, int page, int size) {
        return popularityReportRepository.generatePopularityReport(startDate, endDate, page, size);
    }

    private void validate(String name, String description) {
        if (StringUtils.isBlank(name)) {
            throw new RequestException(RequestErrorMessage.INVALID_NAME);
        }
        if (StringUtils.isBlank(description)) {
            throw new RequestException(RequestErrorMessage.INVALID_DESCRIPTION);
        }
    }

    private ResortListItem convertToResortsList(Resort resort) {
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
        return new ResortListItem(
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

    private ResortDto convertToResortDTO(Resort resort) {
        return new ResortDto(
                resort.getName(),
                resort.getDescription(),
                resort.getAddress().getCity().getCountry(),
                resort.getAddress().getCity().getName(),
                resort.getAddress()
        );
    }

    public record ResortListItem(String resortName, float averageOpinion, String country, String city, String latitude,
                                 String longitude, Float price, LocalDate departureData, LocalDate returnDate
    ) {
    }

    public record ResortDto(String resortName, String description, String country, String city, Address address) {

    }


}
