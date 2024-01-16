package pl.tourpol.backend.api.tour;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tourpol.backend.api.room.RoomDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("/public/api/tours/incoming")
    ResponseEntity<Page<TourDTO>> incomingTours(@RequestBody SearchTourParams params) {
        return ResponseEntity.ok(tourService.getIncomingTours(params));
    }

    @GetMapping("/api/tours/done")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<TourDTO>> doneTours(@RequestParam long resortId,
                                            @RequestParam(required = false) LocalDate departureDate,
                                            @RequestParam(required = false) LocalDate returnDate,
                                            @RequestParam int page) {
        return ResponseEntity.ok(tourService.getDoneTours(resortId, departureDate, returnDate, page));
    }

    @GetMapping("/api/tours/ongoing")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<TourDTO>> outgoingTours(@RequestParam long resortId,
                                                @RequestParam(required = false) LocalDate departureDate,
                                                @RequestParam(required = false) LocalDate returnDate,
                                                @RequestParam int page) {
        return ResponseEntity.ok(tourService.getOngoingTours(resortId, departureDate, returnDate, page));
    }


    @PostMapping("/api/tours/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Long> addTour(@RequestBody NewTourData newTourData) {
        return ResponseEntity.ok(tourService.addTour(newTourData).getId());
    }

    @PostMapping("/api/tours/roomTour/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Long> addRoomToTour(@RequestBody AddRoomToTourRequestDTO dto) {
        return ResponseEntity.ok(tourService.addRoomToTour(dto.roomId, dto.tourId).getId());
    }

    @GetMapping("/public/api/tour/availableRooms")
    ResponseEntity<List<RoomDTO>> getAvailableRooms(@RequestParam long tourId) {
        return ResponseEntity.ok(tourService.getAvailableRooms(tourId)
                .stream()
                .map(RoomDTO::toDto)
                .toList());
    }

    record AddRoomToTourRequestDTO(long roomId, long tourId) {
    }

}
