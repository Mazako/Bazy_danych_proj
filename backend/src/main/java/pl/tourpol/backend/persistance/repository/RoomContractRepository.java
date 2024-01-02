package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.RoomContract;

import java.util.List;

@Repository
public interface RoomContractRepository extends JpaRepository<RoomContract, Long> {

    @Query("SELECT SUM(c.pearsonCount) FROM Contract c WHERE c.tour.id = :tourId AND c.status != 'DONE'")
    Short sumConfirmedPearsonCountForTour(Long tourId);

    @Query("SELECT rc FROM RoomContract rc WHERE rc.contract.id = :contractId")
    List<RoomContract> findRoomContractByContractId(long contractId);
}

