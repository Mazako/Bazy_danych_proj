package pl.tourpol.backend.api.location;

import pl.tourpol.backend.persistance.entity.City;

public record CityDTO(String country,
                      String name,
                      String latitude,
                      String longitude) {
    public static CityDTO toDto(City city) {
        return new CityDTO(city.getCountry(), city.getName(), city.getLatitude(), city.getLongitude());
    }

}
