package pl.tourpol.backend.api.room;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import pl.tourpol.backend.BasicDbTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "classpath:pl/tourpol/backend/api/resort/test_case.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ResortServiceTest extends BasicDbTest {

    @Autowired
    private RoomService roomService;

    @Test
    void whenNoReservedTest() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 8),
                LocalDate.of(2023, 10, 9));

        assertThat(availableRooms).hasSize(3);
    }

    @Test
    void whenContainsStartDayOfReservedTest() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 9),
                LocalDate.of(2023, 10, 10));

        assertThat(mapToNames(availableRooms)).contains("Room3");
    }

    @Test
    void whenContainsReservedRangeTest1() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 11),
                LocalDate.of(2023, 10, 12));

        assertThat(mapToNames(availableRooms)).contains("Room3");
    }

    @Test
    void whenContainsReservedRangeTest2() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 15),
                LocalDate.of(2023, 10, 20));

        assertThat(mapToNames(availableRooms)).isEmpty();
    }

    @Test
    void whenContainsReservedRangeTest3() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 20),
                LocalDate.of(2023, 10, 24));

        assertThat(mapToNames(availableRooms)).isEmpty();
    }

    @Test
    void whenContainsReservedRangeTest4() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 20),
                LocalDate.of(2023, 10, 25));

        assertThat(mapToNames(availableRooms)).isEmpty();
    }

    @Test
    void whenContainsReservedRangeTest5() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 21),
                LocalDate.of(2023, 10, 25));

        assertThat(mapToNames(availableRooms)).contains("Room1", "Room2");
    }

    @Test
    void whenContainsReservedRangeTest6() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 21),
                LocalDate.of(2023, 10, 30));

        assertThat(mapToNames(availableRooms)).contains("Room1", "Room2");
    }

    @Test
    void whenContainsReservedRangeTest7() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2024, 10, 21),
                LocalDate.of(2024, 10, 30));

        assertThat(mapToNames(availableRooms)).hasSize(3);
    }

    @Test
    void whenContainsReservedRangeTest8() {
        List<RoomDTO> availableRooms = roomService.getAvailableRooms(1L,
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 30));

        assertThat(mapToNames(availableRooms)).isEmpty();
    }


    private List<String> mapToNames(List<RoomDTO> availableRooms) {
        return availableRooms.stream()
                .map(RoomDTO::name)
                .toList();
    }

}