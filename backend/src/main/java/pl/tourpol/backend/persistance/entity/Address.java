package pl.tourpol.backend.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_gen")
    @SequenceGenerator(name = "address_id_gen", sequenceName = "address_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Size(max = 15)
    @NotNull
    @Column(name = "building_number", nullable = false, length = 15)
    private String buildingNumber;

    @Size(max = 15)
    @Column(name = "house_number", length = 15)
    private String houseNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public Address() {

    }

    public Address(String street, String buildingNumber, String houseNumber, City city) {
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public Address(String street, String buildingNumber, String houseNumber) {
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.houseNumber = houseNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}