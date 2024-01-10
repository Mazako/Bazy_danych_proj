package pl.tourpol.backend.api.resort;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static pl.tourpol.backend.api.resort.ResortService.ResortDTO;
import static pl.tourpol.backend.api.resort.ResortService.ResortsList;

@RestController
@RequestMapping("/api/resort")
public class ResortController {

    private final ResortService resortService;

    public ResortController(ResortService resortService) {
        this.resortService = resortService;
    }

    @GetMapping()
    public ResponseEntity<ResortDTO> getResortById(@RequestParam Long id){
        ResortDTO resortById = resortService.getResortById(id);
        return resortById != null ? ResponseEntity.ok(resortById) : ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ResortsList>> getResorts(@RequestParam int page){
        Page<ResortsList> allResort = resortService.getAllResort(page);
        return !allResort.isEmpty() ? ResponseEntity.ok(allResort) : ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    public ResponseEntity<Page<ResortsList>> searchResorts(@ModelAttribute SearchParams searchParams, @RequestParam int page) {
        Page<ResortsList> filteredResorts = resortService.searchResorts(searchParams,page);
        return !filteredResorts.isEmpty() ? ResponseEntity.ok(filteredResorts) : ResponseEntity.notFound().build();
    }
    public record SearchParams(
            String resortName,
            String country,
            String city,
            Float minPrice,
            Float maxPrice,
            LocalDate departureDate,
            LocalDate returnDate
    ) {}

}