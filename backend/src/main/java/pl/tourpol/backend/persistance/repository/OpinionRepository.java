package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Opinion;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    @Query("SELECT o FROM Opinion o WHERE o.contract.tour.resort.id = :resortId")
    Page<Opinion> findAllByResortId(long resortId, Pageable pageable);

}
