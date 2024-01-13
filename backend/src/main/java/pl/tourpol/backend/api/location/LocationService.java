package pl.tourpol.backend.api.location;

import org.apache.commons.lang3.StringUtils;
import pl.tourpol.backend.persistance.entity.Address;
import pl.tourpol.backend.persistance.entity.City;
import pl.tourpol.backend.persistance.repository.AddressRepository;
import pl.tourpol.backend.persistance.repository.CityRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public class LocationService {
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;

    public LocationService(CityRepository cityRepository, AddressRepository addressRepository) {
        this.cityRepository = requireNonNull(cityRepository);
        this.addressRepository = requireNonNull(addressRepository);
    }

    public List<City> findCitiesByName(String name) {
        return StringUtils.isBlank(name)
                ? emptyList()
                : cityRepository.findCitiesByName(name).stream().distinct().toList();
    }

    public List<String> findCountries(String name) {
        return StringUtils.isBlank(name) ? emptyList() : cityRepository.findSingleCountry(name);
    }

    public City addCity(String name, String country, String longitude, String latitude) {
        return cityRepository.find(name, country, longitude, latitude)
                .orElseGet(() -> cityRepository.save(new City(country, name, latitude, longitude)));
    }

    public Optional<Address> findAddress(String street, String buildingNumber, String houseNumber, long cityId) {
        return addressRepository.find(street, buildingNumber, houseNumber, cityId);
    }

    public Address addAddress(String street, String buildingNumber, String houseNumber, long cityId) {
        return addressRepository.find(street, buildingNumber, houseNumber, cityId)
                .orElseGet(() -> {
                    City city = cityRepository.findById(cityId).orElseThrow(RuntimeException::new);
                    return addressRepository.save(new Address(street, buildingNumber, houseNumber, city));
                });
    }

    public Address addLocation(String street, String buildingNumber, String houseNumber,
                              String cityName, String cityCountry, String cityLongitude, String cityLatitude) {
        var cityId = addCity(cityName, cityCountry, cityLongitude, cityLatitude).getId();
        return addAddress(street, buildingNumber, houseNumber, cityId);
    }

}
