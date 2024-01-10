package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Notification;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.contract.user.id = :userId")
    List<Notification> getAllNotificationByUserId(long userId);

}
