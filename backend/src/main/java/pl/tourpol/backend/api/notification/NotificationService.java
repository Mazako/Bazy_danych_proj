package pl.tourpol.backend.api.notification;

import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Notification;
import pl.tourpol.backend.persistance.entity.NotificationType;
import pl.tourpol.backend.persistance.repository.ContractRepository;
import pl.tourpol.backend.persistance.repository.NotificationRepository;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;
import pl.tourpol.backend.user.UserService;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static pl.tourpol.backend.security.permissions.AccessSensitiveOperationType.NOTIFICATION_ACCESS;

public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final ContractRepository contractRepository;
    private final AccessSensitiveOperation accessSensitiveOperation;

    public NotificationService(NotificationRepository notificationRepository, UserService userService, ContractRepository contractRepository, AccessSensitiveOperation accessSensitiveOperation) {
        this.notificationRepository = requireNonNull(notificationRepository);
        this.userService = requireNonNull(userService);
        this.contractRepository = requireNonNull(contractRepository);
        this.accessSensitiveOperation = requireNonNull(accessSensitiveOperation);
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

    @Transactional
    public Notification sendNotification(long contractId, String content, NotificationTypeValue notificationTypeValue) {
        var contract = contractRepository.findById(contractId).orElseThrow(() -> new RequestException(RequestErrorMessage.CONTRACT_NOT_EXISTS));
        var type = notificationRepository.findNotificationTypeById(notificationTypeValue.getId()).orElseThrow(() -> new RequestException(RequestErrorMessage.NOTIFICATION_NOT_FOUND));

        return notificationRepository.save(new Notification(content, false, LocalDate.now(), contract, type));
    }
    @Transactional
    public void setNotificationAsSeen(long notificationId) {
        accessSensitiveOperation.callWithAccessCheck(notificationId, this::wrap, NOTIFICATION_ACCESS);
    }

    private Void wrap(Long id) {
        notificationRepository.setNotificationAsSeen(id);
        return null;
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
