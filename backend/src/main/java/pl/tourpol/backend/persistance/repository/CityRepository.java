package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.tourpol.backend.persistance.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM city
            WHERE name ILIKE CONCAT(?1, '%')
            """)
    List<City> findCitiesByName(String name);

    @Query(nativeQuery = true, value = """
              SELECT DISTINCT country FROM city
              WHERE country ILIKE CONCAT(?1, '%')
            """)
    List<String> findSingleCountry(String country);

    @Query(nativeQuery = true, value = """
            SELECT * from city
            WHERE name = ?1 AND country = ?2 AND longitude = ?3 AND latitude = ?4
            UNION
            SELECT * FROM city
            WHERE name = ?1 AND country = ?2
            AND ?3 IS NULL AND longitude IS NULL
            AND ?4 IS NULL AND latitude IS NULL
            UNION
            SELECT * FROM city
            WHERE name = ?1 AND country = ?2 AND latitude = ?4
            AND ?3 IS NULL AND longitude IS NULL
            UNION
            SELECT * FROM city
            WHERE name = ?1 AND country = ?2 AND longitude = ?3
            AND ?4 IS NULL AND latitude IS NULL
            LIMIT 1
            """)
    Optional<City> find(String name, String country, String longitude, String latitude);

}
