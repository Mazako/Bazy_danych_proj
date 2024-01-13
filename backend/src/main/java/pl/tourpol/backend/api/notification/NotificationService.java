package pl.tourpol.backend.api.notification;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Notification;
import pl.tourpol.backend.persistance.entity.NotificationType;
import pl.tourpol.backend.persistance.repository.NotificationRepository;
import pl.tourpol.backend.user.UserService;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = requireNonNull(notificationRepository);
        this.userService = requireNonNull(userService);
    }

    public List<NotificationDTO> getNotificationsByUserInSession() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = user.getUsername();
        AppUser appUser = userService.getUserByEmail(mail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));
        List<Notification> allNotificationByUserId = notificationRepository.getAllNotificationByUserId(appUser.getId());
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
    ) {
    }


}
