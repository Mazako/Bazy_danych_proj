package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tourpol.backend.persistance.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
