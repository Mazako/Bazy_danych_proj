package pl.tourpol.backend.api.room;

import pl.tourpol.backend.persistance.entity.Room;
import pl.tourpol.backend.persistance.entity.RoomContract;

public record RoomDTO(String name, short personCount, short standard) {
    public static RoomDTO toDto(Room room) {
        return new RoomDTO(room.getName(), room.getPersonCount(), room.getStandard());
    }

    public static RoomDTO toDto(RoomContract roomContract) {
        Room room = roomContract.getRoom();
        return new RoomDTO(room.getName(), room.getPersonCount(), room.getStandard());

    }
}
