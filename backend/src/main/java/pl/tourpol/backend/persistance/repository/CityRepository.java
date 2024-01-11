package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tourpol.backend.persistance.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
