package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.user.id = :userId")
    List<Contract> findAllByUserId(long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM refund_factor(?1)")
    float getRefundFactor(long contractId);
}
