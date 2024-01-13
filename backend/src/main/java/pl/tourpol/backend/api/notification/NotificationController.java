package pl.tourpol.backend.api.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static pl.tourpol.backend.api.notification.NotificationService.NotificationDTO;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = requireNonNull(notificationService);
    }

    @GetMapping
    public ResponseEntity<?> getNotifications() {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserInSession();
        return notifications != null ? ResponseEntity.ok(notifications) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/setAsSeen")
    public ResponseEntity<?> setNotificationAsSeen(@RequestParam long notificationId) {
        notificationService.setNotificationAsSeen(notificationId);
        return ResponseEntity.ok().build();
    }

}
