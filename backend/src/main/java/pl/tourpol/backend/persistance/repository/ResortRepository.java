package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Resort;

import java.time.LocalDate;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {

    @Query("SELECT r FROM Resort r")
    Page<Resort> findAllResorts(Pageable pageable);


    @Query("SELECT r FROM Resort r JOIN r.address a JOIN a.city c " +
            "WHERE (COALESCE(:resortName, '') = '' OR LOWER(r.name) LIKE LOWER(concat('%', :resortName, '%'))) " +
            "AND (COALESCE(:city, '') = '' OR LOWER(c.name) LIKE LOWER(concat('%', :city, '%'))) " +
            "AND (COALESCE(:country, '') = '' OR LOWER(c.country) LIKE LOWER(concat('%', :country, '%'))) " +
            "AND r.id IN (" +
            "  SELECT rt.resort.id FROM Tour rt " +
            "  WHERE (:minPrice IS NULL OR rt.price >= :minPrice) " +
            "  AND (:maxPrice IS NULL OR rt.price <= :maxPrice) " +
            "  AND (:departureDate IS NULL OR rt.departureDate >= :departureDate) " +
            "  AND (:returnDate IS NULL OR rt.returnDate <= :returnDate))")
    Page<Resort> findResortWithFilters(@Param("resortName") String resortName,
                                       @Param("city") String city,
                                       @Param("country") String country,
                                       @Param("minPrice") Float minPrice,
                                       @Param("maxPrice") Float maxPrice,
                                       @Param("departureDate") LocalDate departureDate,
                                       @Param("returnDate") LocalDate returnDate,
                                       Pageable pageable);

}
