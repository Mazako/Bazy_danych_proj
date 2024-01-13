package pl.tourpol.backend.api.resort;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.tourpol.backend.api.location.AddressDTO;
import pl.tourpol.backend.api.room.RoomDTO;

import java.util.List;

public record NewResortData(String name,
                            String description,
                            @JsonProperty("address") AddressDTO addressDTO,
                            List<RoomDTO> rooms) {
}