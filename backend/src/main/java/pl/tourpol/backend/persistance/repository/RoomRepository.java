package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT * FROM get_available_rooms(?1, ?2, ?3)", nativeQuery = true)
    List<Room> getAvailableRooms(long resortId, LocalDate startDate, LocalDate endDate);

    @Query(nativeQuery = true, value = "SELECT * FROM is_room_available(?1, ?2, ?3)")
    boolean isRoomAvailable(long roomId, LocalDate startDate, LocalDate endDate);

}
