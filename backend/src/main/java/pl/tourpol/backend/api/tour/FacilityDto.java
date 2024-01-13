package pl.tourpol.backend.api.tour;

import pl.tourpol.backend.persistance.entity.Facility;

public record FacilityDto(boolean wifi, boolean swimmingPool, boolean airConditioning,
                          boolean gym, boolean food, boolean roomService,
                          boolean bar, boolean restaurant, boolean freeParking,
                          boolean allTimeReception) {

    public Facility toEntity() {
        return new Facility(wifi,
                swimmingPool,
                airConditioning,
                gym,
                food,
                roomService,
                bar,
                restaurant,
                freeParking,
                allTimeReception);
    }
}