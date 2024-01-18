package pl.tourpol.backend.api.tour;

import pl.tourpol.backend.persistance.entity.Facility;
import pl.tourpol.backend.persistance.entity.Tour;

import java.time.LocalDate;

public record TourDTO(Facility facilities, Long id,String name, String description, float price, LocalDate departureDate, LocalDate returnDate,
                      short roomsCount, short placesLeft) {
    public static TourDTO convertToTourDTO(Tour tour, short totalCapacity, short placesLeft) {
        return new TourDTO(
                tour.getFacility(),
                tour.getId(),
                tour.getName(),
                tour.getDescription(),
                tour.getPrice(),
                tour.getDepartureDate(),
                tour.getReturnDate(),
                totalCapacity,
                placesLeft
        );
    }
}