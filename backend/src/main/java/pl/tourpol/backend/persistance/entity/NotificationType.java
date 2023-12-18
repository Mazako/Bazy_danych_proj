package pl.tourpol.backend.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "notification_type")
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_type_id_gen")
    @SequenceGenerator(name = "notification_type_id_gen", sequenceName = "notification_type_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 35)
    @NotNull
    @Column(name = "type", nullable = false, length = 35)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}