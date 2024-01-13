package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.IncomingTour;
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

    @SuppressWarnings("java:S107")
    @Query(value = """
            SELECT t FROM IncomingTour t
            WHERE t.resortId = :resortId
            AND (COALESCE(:name, '') = '' OR t.name ILIKE CONCAT('%', :name, '%'))
            AND (:price IS NULL OR t.price <= :price)
            AND (:departureDate IS NULL OR t.departureDate >= :departureDate)
            AND (:returnDate IS NULL OR t.returnDate <= :returnDate)
            AND (:wiFi IS NULL OR t.wifi = :wiFi)
            AND (:swimmingPool IS NULL OR t.swimmingPool = :swimmingPool)
            AND (:airConditioning IS NULL OR t.airConditioning = :airConditioning)
            AND (:gym IS NULL OR t.gym = :gym)
            AND (:food IS NULL OR t.food = :food)
            AND (:roomService IS NULL OR t.roomService = :roomService)
            AND (:bar IS NULL OR t.bar = :bar)
            AND (:restaurant IS NULL OR t.restaurant = :restaurant)
            AND (:freeParking IS NULL OR t.freeParking = :freeParking)
            AND (:allTimeReception IS NULL OR t.allTimeReception = :allTimeReception)
            """)
    Page<IncomingTour> findIncomingTours(@Param("resortId") long resortId,
                                         @Param("name") String name,
                                         @Param("price") Float price,
                                         @Param("departureDate") LocalDate departureDate,
                                         @Param("returnDate") LocalDate returnDate,
                                         @Param("wiFi") Boolean wiFi,
                                         @Param("swimmingPool") Boolean swimmingPool,
                                         @Param("airConditioning") Boolean airConditioning,
                                         @Param("gym") Boolean gym,
                                         @Param("food") Boolean food,
                                         @Param("roomService") Boolean roomService,
                                         @Param("bar") Boolean bar,
                                         @Param("restaurant") Boolean restaurant,
                                         @Param("freeParking") Boolean freeParking,
                                         @Param("allTimeReception") Boolean allTimeReception,
                                         Pageable pageable);

}


