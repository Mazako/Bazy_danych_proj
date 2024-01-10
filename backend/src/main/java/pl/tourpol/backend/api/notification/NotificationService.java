package pl.tourpol.backend.api.notification;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    private final UserService userService;
    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    public List<NotificationDTO> getNotificationsByUserInSession(){
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
    ) { }


}
