package pl.tourpol.backend.api.resort;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static pl.tourpol.backend.api.resort.ResortService.ResortDto;
import static pl.tourpol.backend.api.resort.ResortService.ResortListItem;

@RestController
class ResortController {

    private final ResortService resortService;

    @Autowired
    ResortController(ResortService resortService) {
        this.resortService = requireNonNull(resortService);
    }

    @GetMapping()
    ResponseEntity<ResortDto> getResortById(@RequestParam Long id){
        ResortDto resortById = resortService.getResortById(id);
        return resortById != null ? ResponseEntity.ok(resortById) : ResponseEntity.notFound().build();
    }

    @GetMapping("/public/api/resort/list")
    ResponseEntity<Page<ResortListItem>> getAllResorts(@RequestParam int page){
        Page<ResortListItem> allResort = resortService.getAllResort(page);
        return !allResort.isEmpty() ? ResponseEntity.ok(allResort) : ResponseEntity.notFound().build();
    }
    @PostMapping("/public/api/resort/search")
    ResponseEntity<Page<ResortListItem>> searchResorts(@RequestBody SearchRequestDTO searchParams) {
        Page<ResortListItem> filteredResorts = resortService.searchResorts(searchParams);
        return !filteredResorts.isEmpty() ? ResponseEntity.ok(filteredResorts) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api/resort/add")
    ResponseEntity<Long> addResort(@RequestBody NewResortData newResortData) {
        return ResponseEntity.ok(resortService.addResort(newResortData).getId());
    }
    public record SearchRequestDTO(
            String resortName,
            String country,
            String city,
            Float minPrice,
            Float maxPrice,
            LocalDate departureDate,
            LocalDate returnDate,
            int page
    ) {}

}
