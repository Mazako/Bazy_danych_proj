package pl.tourpol.backend.persistance.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "done_tours")
public class DoneTours extends TourViewEntity {
}