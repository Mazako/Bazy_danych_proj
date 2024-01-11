package pl.tourpol.backend.api.resort;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static pl.tourpol.backend.api.resort.ResortService.ResortDTO;
import static pl.tourpol.backend.api.resort.ResortService.ResortsList;

@RestController
@RequestMapping("public/api/resort")
class ResortController {

    private final ResortService resortService;

    @Autowired
    ResortController(ResortService resortService) {
        this.resortService = requireNonNull(resortService);
    }

    @GetMapping()
    ResponseEntity<ResortDTO> getResortById(@RequestParam Long id){
        ResortDTO resortById = resortService.getResortById(id);
        return resortById != null ? ResponseEntity.ok(resortById) : ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    ResponseEntity<Page<ResortsList>> getAllResorts(@RequestParam int page){
        Page<ResortsList> allResort = resortService.getAllResort(page);
        return !allResort.isEmpty() ? ResponseEntity.ok(allResort) : ResponseEntity.notFound().build();
    }
    @PostMapping("/search")
    ResponseEntity<Page<ResortsList>> searchResorts(@RequestBody SearchRequestDTO searchParams) {
        Page<ResortsList> filteredResorts = resortService.searchResorts(searchParams);
        return !filteredResorts.isEmpty() ? ResponseEntity.ok(filteredResorts) : ResponseEntity.notFound().build();
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
