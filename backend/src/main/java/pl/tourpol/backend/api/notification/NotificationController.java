package pl.tourpol.backend.api.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static pl.tourpol.backend.api.notification.NotificationService.NotificationDTO;
import java.util.List;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

     @GetMapping()
    public ResponseEntity<?> getNotifications() {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserInSession();
        return notifications != null ? ResponseEntity.ok(notifications) : ResponseEntity.notFound().build();
    }
}
