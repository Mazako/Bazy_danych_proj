package pl.tourpol.backend.api.tour;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record NewTourData(long resortId,
                          String name,
                          String description,
                          float price,
                          LocalDate departureDate,
                          LocalDate returnDate,
                          FacilityDto facilities,
                          List<Long> roomIds) {
}
