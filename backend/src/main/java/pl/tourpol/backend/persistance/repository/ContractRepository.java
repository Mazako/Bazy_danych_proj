package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Contract;

import java.util.Collection;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(value = "SELECT * FROM contract c WHERE c.user_id = :userId AND CAST(c.status AS TEXT) IN :statuses", nativeQuery = true)
    Page<Contract> findAllByUserIdAndStatuses(
            @Param("userId") long userId,
            @Param("statuses") List<String> statuses,
            Pageable pageable
    );

    @Query(nativeQuery = true, value = "SELECT * FROM refund_factor(?1)")
    float getRefundFactor(long contractId);
}
