package pl.tourpol.backend.persistance.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
@Entity
@Immutable
@Table(name = "full_opinion_info")
public class FullOpinionInfo {
    @Column(name = "resort_id")
    private Long resortId;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "opinion_id")
    @Id
    private Long opinionId;

    @Column(name = "send_date")
    private LocalDate sendDate;

    @Size(max = 1000)
    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "rate")
    private Short rate;

    @Size(max = 20)
    @Column(name = "user_name", length = 20)
    private String userName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    public Long getResortId() {
        return resortId;
    }

    public String getName() {
        return name;
    }

    public Long getOpinionId() {
        return opinionId;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }

    public String getComment() {
        return comment;
    }

    public Short getRate() {
        return rate;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    protected FullOpinionInfo() {
    }
}