package pl.tourpol.backend.persistance.view;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@MappedSuperclass
public abstract class TourViewEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "swimming_pool")
    private Boolean swimmingPool;

    @Column(name = "air_conditioning")
    private Boolean airConditioning;

    @Column(name = "gym")
    private Boolean gym;

    @Column(name = "food")
    private Boolean food;

    @Column(name = "room_service")
    private Boolean roomService;

    @Column(name = "bar")
    private Boolean bar;

    @Column(name = "restaurant")
    private Boolean restaurant;

    @Column(name = "free_parking")
    private Boolean freeParking;

    @Column(name = "all_time_reception")
    private Boolean allTimeReception;

    @Column(name = "resort_id")
    private Long resortId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public Boolean getSwimmingPool() {
        return swimmingPool;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public Boolean getGym() {
        return gym;
    }

    public Boolean getFood() {
        return food;
    }

    public Boolean getRoomService() {
        return roomService;
    }

    public Boolean getBar() {
        return bar;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public Boolean getFreeParking() {
        return freeParking;
    }

    public Boolean getAllTimeReception() {
        return allTimeReception;
    }

    public Long getResortId() {
        return resortId;
    }

    protected TourViewEntity() {
    }
}