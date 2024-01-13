package pl.tourpol.backend.api.tour;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
