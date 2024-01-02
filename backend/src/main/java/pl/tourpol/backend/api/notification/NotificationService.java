package pl.tourpol.backend.api.notification;

import org.springframework.stereotype.Service;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Notification;
import pl.tourpol.backend.persistance.entity.NotificationType;
import pl.tourpol.backend.persistance.repository.NotificationRepository;
import pl.tourpol.backend.security.JwtService;
import pl.tourpol.backend.user.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JwtService jwtService;
    private final UserService userService;
    public NotificationService(NotificationRepository notificationRepository, JwtService jwtService, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public List<NotificationDTO> getNotificationsByUserInSession(String token){
        String userEmail = jwtService.extractUserName(token.replace("Bearer ", ""));
        AppUser user = userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new NoSuchElementException("Błędny użytkownik w sesji"));
        List<Notification> allNotificationByUserId = notificationRepository.getAllNotificationByUserId(user.getId());
        return allNotificationByUserId.stream()
                .map(this::convertToNotificationDTO)
                .toList();
    }

    private NotificationDTO convertToNotificationDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getContent(),
                notification.getSeen(),
                notification.getSendDate(),
                notification.getContract().getTour().getName(),
                notification.getContract().getTour().getDepartureDate(),
                notification.getContract().getTour().getReturnDate(),
                notification.getNotificationType(),
                notification.getContract().getReservationDate(),
                notification.getContract().getPearsonCount()
        );
    }

    public record NotificationDTO(
            Long id,
            String content,
            boolean seen,
            LocalDate sendDate,
            String tourName,
            LocalDate departureDate,
            LocalDate returnDate,
            NotificationType type,
            LocalDate reservationDate,
            short pearsonCount
    ) { }


}
