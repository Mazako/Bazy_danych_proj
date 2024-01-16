package pl.tourpol.backend.api.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/public/api/room/availableRooms")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<RoomDTO>> getAvailableRooms(@RequestParam long resortId,
                                                    @RequestParam LocalDate startDate,
                                                    @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(roomService.getAvailableRooms(resortId, startDate, endDate));
    }

    @PostMapping("/api/room/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addRoom(@RequestBody AddRoomDto roomDTO) {
        roomService.addRoom(roomDTO.resortId, roomDTO.name, roomDTO.pearsonCount, roomDTO.standard);
        return ResponseEntity.ok(roomService.addRoom(roomDTO.resortId, roomDTO.name, roomDTO.pearsonCount, roomDTO.standard).getId());
    }

    record AddRoomDto(long resortId, String name, short pearsonCount, short standard) {
    }

}
