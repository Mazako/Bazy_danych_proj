package pl.tourpol.backend.api.resort;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tourpol.backend.persistance.PopularityEntry;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/api/resort/popularityReport")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<PopularityEntry>> getPopularityReport(@RequestParam LocalDate startDate,
                                                              @RequestParam LocalDate endDate,
                                                              @RequestParam int page,
                                                              @RequestParam int size) {
        return ResponseEntity.ok(resortService.generatePopularityReport(startDate, endDate, page, size));
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
