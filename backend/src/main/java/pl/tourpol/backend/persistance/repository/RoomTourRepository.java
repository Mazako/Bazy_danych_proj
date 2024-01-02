package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.RoomTour;

import java.util.List;

@Repository
public interface RoomTourRepository extends JpaRepository<RoomTour, Long> {
    @Query("SELECT COUNT(rt) FROM RoomTour rt WHERE rt.tour.id = :tourId")
    Long countByTourId(Long tourId);

    @Query("SELECT SUM(rt.room.personCount) FROM RoomTour rt WHERE rt.tour.id = :tourId")
    Short sumTotalCapacityForTour(Long tourId);
}