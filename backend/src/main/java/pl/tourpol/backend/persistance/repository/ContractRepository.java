package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Contract;

import java.util.Collection;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.user.id = :userId AND c.status IN :statuses")
    Page<Contract> findAllByUserIdAndStatuses(@Param("userId") long userId, @Param("statuses") Collection<Contract.Status> statuses, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM refund_factor(?1)")
    float getRefundFactor(long contractId);
}
