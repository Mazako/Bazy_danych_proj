package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Tour;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query("SELECT t FROM Tour t WHERE t.resort.id = :resortId")
    List<Tour> findToursByResortId(@Param("resortId") Long resortId);

    @Query("SELECT t FROM Tour t WHERE t.resort.id = :resortId AND t.departureDate >= :today ORDER BY t.departureDate ASC")
    Optional<Tour> findNearestUpcomingTourForResort(Long resortId, LocalDate today);

}


