package pl.tourpol.backend.api.room;

import pl.tourpol.backend.persistance.entity.Room;
import pl.tourpol.backend.persistance.repository.ResortRepository;
import pl.tourpol.backend.persistance.repository.RoomRepository;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class RoomService {
    private final RoomRepository roomRepository;
    private final ResortRepository resortRepository;

    public RoomService(RoomRepository roomRepository, ResortRepository resortRepository) {
        this.roomRepository = requireNonNull(roomRepository);
        this.resortRepository = requireNonNull(resortRepository);
    }

    public List<RoomDTO> getAvailableRooms(long resortId, LocalDate startDate, LocalDate endDate) {
        return roomRepository.getAvailableRooms(resortId, startDate, endDate)
                .stream()
                .map(RoomDTO::toDto)
                .toList();
    }

    public Room addRoom(long resortId, String name, short pearsonCount, short standard) {
        var resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new RequestException(RequestErrorMessage.RESORT_NOT_EXISTS));

        var room = new Room(name, pearsonCount, standard);
        validate(room);
        room.setResort(resort);
        return roomRepository.save(room);
    }

    public boolean isRoomAvailable(long roomId, LocalDate startDate, LocalDate endDate) {
        if (roomRepository.findById(roomId).isEmpty()) {
            throw new RequestException(RequestErrorMessage.ROOM_NOT_EXISTS);
        }
        return roomRepository.isRoomAvailable(roomId, startDate, endDate);
    }

    public Optional<Room> getRoomById(long roomId) {
        return roomRepository.findById(roomId);
    }

    public Optional<Room> getAvailableRoomById(long roomId, LocalDate startDate, LocalDate endDate) {
        return roomRepository.findById(roomId)
                .filter(room -> isRoomAvailable(roomId, startDate, endDate));
    }

    public boolean deleteRoom(long roomId) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    roomRepository.delete(room);
                    return true;
                })
                .orElse(false);
    }

    private void validate(Room room) {
        if (room.getPersonCount() < 0) {
            throw new RequestException(RequestErrorMessage.ROOM_ADD_INVALID_PERSON_COUNT);
        }
        if (room.getStandard() < 1 || room.getStandard() > 3) {
            throw new RequestException(RequestErrorMessage.ROOM_ADD_INVALID_STANDARD);
        }
    }


}
