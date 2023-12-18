package pl.tourpol.backend.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import pl.tourpol.backend.persistance.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}