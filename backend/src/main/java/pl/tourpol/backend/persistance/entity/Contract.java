package pl.tourpol.backend.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_id_gen")
    @SequenceGenerator(name = "contract_id_gen", sequenceName = "contract_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @NotNull
    @Column(name = "pearson_count", nullable = false)
    private Short pearsonCount;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING_PAYMENT,
        CANCELLED,
        PAID,
        IN_PROGRESS,
        DONE
    }

    public Contract() {

    }

    public Contract(LocalDate reservationDate, Short pearsonCount, AppUser user, Tour tour, Status status) {
        this.reservationDate = reservationDate;
        this.pearsonCount = pearsonCount;
        this.user = user;
        this.tour = tour;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Short getPearsonCount() {
        return pearsonCount;
    }

    public void setPearsonCount(Short pearsonCount) {
        this.pearsonCount = pearsonCount;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}