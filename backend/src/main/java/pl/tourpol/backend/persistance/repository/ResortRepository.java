package pl.tourpol.backend.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Resort;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {

    @Query("SELECT r FROM Resort r")
    Page<Resort> findAllResorts(Pageable pageable);
}
