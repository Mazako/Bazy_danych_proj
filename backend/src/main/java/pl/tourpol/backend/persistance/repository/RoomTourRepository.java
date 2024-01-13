package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Room;
import pl.tourpol.backend.persistance.entity.RoomTour;

import java.util.List;

@Repository
public interface RoomTourRepository extends JpaRepository<RoomTour, Long> {
    @Query("SELECT COUNT(rt) FROM RoomTour rt WHERE rt.tour.id = :tourId")
    Long countByTourId(Long tourId);

    @Query("SELECT SUM(rt.room.personCount) FROM RoomTour rt WHERE rt.tour.id = :tourId")
    Short sumTotalCapacityForTour(Long tourId);

    @Query(nativeQuery = true, value = """
            SELECT room.* FROM room_tour
            INNER JOIN tour ON room_tour.tour_id = tour.id
            INNER JOIN room ON room_tour.room_id = room.id
            WHERE tour_id = ?1
            """)
    List<Room> getRoomsAssignedToTour(long tourId);
}