package pl.tourpol.backend.api.location;

import pl.tourpol.backend.persistance.entity.City;
import pl.tourpol.backend.persistance.repository.AddressRepository;
import pl.tourpol.backend.persistance.repository.CityRepository;

import static java.util.Objects.requireNonNull;

public class LocationService {
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;

    public LocationService(CityRepository cityRepository, AddressRepository addressRepository) {
        this.cityRepository = requireNonNull(cityRepository);
        this.addressRepository = requireNonNull(addressRepository);
    }

    public void addCityIfNotExists(String name, String country) {
        cityRepository.save(new City(name, country));
    }

    public void addCityIfNotExists(String name, String country, String longitude, String latitude) {
        cityRepository.save(new City(name, country, longitude, latitude));
    }
}
