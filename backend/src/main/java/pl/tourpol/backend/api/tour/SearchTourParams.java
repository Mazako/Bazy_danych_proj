package pl.tourpol.backend.api.tour;

import java.time.LocalDate;

public record SearchTourParams(long resortId,
                               String name,
                               Float price,
                               LocalDate departureDate,
                               LocalDate returnDate,
                               Boolean wiFi,
                               Boolean swimmingPool,
                               Boolean airConditioning,
                               Boolean gym,
                               Boolean food,
                               Boolean roomService,
                               Boolean bar,
                               Boolean restaurant,
                               Boolean freeParking,
                               Boolean allTimeReception,
                               int page) {
}
