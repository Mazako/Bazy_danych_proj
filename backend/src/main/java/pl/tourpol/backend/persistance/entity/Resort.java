package pl.tourpol.backend.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "resort")
public class Resort {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resort_id_gen")
    @SequenceGenerator(name = "resort_id_gen", sequenceName = "resort_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "avg_opinion")
    private Float avgOpinion;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    public Resort() {

    }

    public Resort(String name, Address address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Float getAvgOpinion() {
        return avgOpinion;
    }

    public void setAvgOpinion(Float avgOpinion) {
        this.avgOpinion = avgOpinion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}