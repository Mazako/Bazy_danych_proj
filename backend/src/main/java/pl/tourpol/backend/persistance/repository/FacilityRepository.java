package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.tourpol.backend.persistance.entity.Facility;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM facility
            WHERE wifi = :wiFi
            AND swimming_pool = :swimmingPool
            AND air_conditioning = :airConditioning
            AND gym = :gym
            AND food = :food
            AND room_service = :roomService
            AND bar = :bar
            AND restaurant = :restaurant
            AND free_parking = :freeParking
            AND all_time_reception = :allTimeReception
            """)
    Optional<Facility> findFacility(@Param("wiFi") boolean wiFi,
                                    @Param("swimmingPool") boolean swimmingPool,
                                    @Param("airConditioning") boolean airConditioning,
                                    @Param("gym") boolean gym,
                                    @Param("food") boolean food,
                                    @Param("roomService") boolean roomService,
                                    @Param("bar") boolean bar,
                                    @Param("restaurant") boolean restaurant,
                                    @Param("freeParking") boolean freeParking,
                                    @Param("allTimeReception") boolean allTimeReception);
}
