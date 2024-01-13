package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.tourpol.backend.persistance.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM address
            WHERE street = ?1 AND building_number = ?2 AND house_number = ?3 AND city_id = ?4
            """)
    Optional<Address> find(String street, String buildingNumber, String houseNumber, long cityId);

}
