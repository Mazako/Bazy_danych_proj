package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Opinion;
import pl.tourpol.backend.persistance.view.FullOpinionInfo;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    @Query("SELECT o FROM FullOpinionInfo o WHERE o.resortId = :resortId")
    Page<FullOpinionInfo> findAllByResortId(long resortId, Pageable pageable);

    @Query(nativeQuery = true, value = """
        SELECT CASE WHEN COUNT(opinion.id) > 0 THEN TRUE ELSE FALSE END from opinion
        INNER JOIN contract ON opinion.contract_id = contract.id
        WHERE contract_id = ?1
        """)
    boolean isOpinionAdded(long contractId);


}
