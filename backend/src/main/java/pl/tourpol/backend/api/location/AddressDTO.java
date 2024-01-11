package pl.tourpol.backend.api.location;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressDTO(String street,
                         String buildingNumber,
                         String houseNumber,
                         @JsonProperty("city") CityDTO city) {
}
