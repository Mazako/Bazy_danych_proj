package pl.tourpol.backend.api.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

    // TOKEN TESTOWY DO SWAGGERA
    // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwubWF6aWFyejEyQGdtYWlsLmNvbSIsImlhdCI6MTcwNDIxMzI4OSwiZXhwIjoxNzA0MjIwNDg5fQ.dOuh1U53iI2IZ1DGegOBtSdvq6GhYCOsEwqFdq1ZqbI
    @GetMapping()
    public ResponseEntity<?> getNotifications(@RequestHeader String token) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserInSession(token);
        return notifications != null ? ResponseEntity.ok(notifications) : ResponseEntity.notFound().build();
    }
}
