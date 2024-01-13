package pl.tourpol.backend.api.location;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/public/api/location/cities")
    ResponseEntity<List<CityDTO>> findCites(@RequestParam String name) {
        List<CityDTO> result = locationService.findCitiesByName(name)
                .stream()
                .map(CityDTO::toDto)
                .toList();

        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }
    @GetMapping("/public/api/location/countries")
    ResponseEntity<List<String>> findCountries(@RequestParam String name) {
        List<String> countries = locationService.findCountries(name);
        return countries.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(countries);
    }

    @PostMapping("/api/location/addCity")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Long> addCity(@RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(locationService.addCity(cityDTO.name(), cityDTO.country(), cityDTO.longitude(), cityDTO.latitude()).getId());
    }

    @PostMapping("/api/location/addAddress")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Long> addAddress(@RequestBody AddAddressRequestDTO addressDTO) {
        return ResponseEntity.ok(locationService.addAddress(addressDTO.street, addressDTO.buildingNumber, addressDTO.houseNumber, addressDTO.cityId).getId());
    }

    record AddAddressRequestDTO(String street, String buildingNumber, String houseNumber, long cityId) {

    }
}
