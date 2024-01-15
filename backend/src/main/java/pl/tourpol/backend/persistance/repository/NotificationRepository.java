package pl.tourpol.backend.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.entity.Notification;
import pl.tourpol.backend.persistance.entity.NotificationType;

import java.util.List;
import java.util.Optional;


@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.contract.user.id = :userId")
    List<Notification> getAllNotificationByUserId(long userId);

    @Query(nativeQuery = true, value = """
            UPDATE notification SET seen = true WHERE id = ?1
            """)
    @Modifying
    void setNotificationAsSeen(long notificationId);

    @Query("SELECT nt FROM NotificationType nt WHERE nt.id = ?1")
    Optional<NotificationType> findNotificationTypeById(long notificationTypeId);

}
