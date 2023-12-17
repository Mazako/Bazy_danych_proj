package pl.tourpol.backend.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByMail(String mail);
}
