package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import pl.tourpol.backend.persistance.entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    @Query("SELECT vt FROM VerificationToken vt WHERE vt.token = ?1")
    Optional<VerificationToken> findTokenByName(String token);

    @Procedure("remove_expired_tokens")
    void removeExpiredTokens();
}