package pl.tourpol.backend.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_id_gen")
    @SequenceGenerator(name = "facility_id_gen", sequenceName = "facility_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "wifi", nullable = false)
    private Boolean wifi = false;

    @NotNull
    @Column(name = "swimming_pool", nullable = false)
    private Boolean swimmingPool = false;

    @NotNull
    @Column(name = "air_conditioning", nullable = false)
    private Boolean airConditioning = false;

    @NotNull
    @Column(name = "gym", nullable = false)
    private Boolean gym = false;

    @NotNull
    @Column(name = "food", nullable = false)
    private Boolean food = false;

    @NotNull
    @Column(name = "room_service", nullable = false)
    private Boolean roomService = false;

    @NotNull
    @Column(name = "bar", nullable = false)
    private Boolean bar = false;

    @NotNull
    @Column(name = "restaurant", nullable = false)
    private Boolean restaurant = false;

    @NotNull
    @Column(name = "free_parking", nullable = false)
    private Boolean freeParking = false;

    @NotNull
    @Column(name = "all_time_reception", nullable = false)
    private Boolean allTimeReception = false;

    public Facility(Boolean wifi, Boolean swimmingPool, Boolean airConditioning, Boolean gym, Boolean food, Boolean roomService, Boolean bar, Boolean restaurant, Boolean freeParking, Boolean allTimeReception) {
        this.wifi = wifi;
        this.swimmingPool = swimmingPool;
        this.airConditioning = airConditioning;
        this.gym = gym;
        this.food = food;
        this.roomService = roomService;
        this.bar = bar;
        this.restaurant = restaurant;
        this.freeParking = freeParking;
        this.allTimeReception = allTimeReception;
    }

    public Facility() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(Boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public Boolean getGym() {
        return gym;
    }

    public void setGym(Boolean gym) {
        this.gym = gym;
    }

    public Boolean getFood() {
        return food;
    }

    public void setFood(Boolean food) {
        this.food = food;
    }

    public Boolean getRoomService() {
        return roomService;
    }

    public void setRoomService(Boolean roomService) {
        this.roomService = roomService;
    }

    public Boolean getBar() {
        return bar;
    }

    public void setBar(Boolean bar) {
        this.bar = bar;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getFreeParking() {
        return freeParking;
    }

    public void setFreeParking(Boolean freeParking) {
        this.freeParking = freeParking;
    }

    public Boolean getAllTimeReception() {
        return allTimeReception;
    }

    public void setAllTimeReception(Boolean allTimeReception) {
        this.allTimeReception = allTimeReception;
    }

}