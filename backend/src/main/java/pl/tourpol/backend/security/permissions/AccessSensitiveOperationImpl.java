package pl.tourpol.backend.security.permissions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.repository.ContractRepository;
import pl.tourpol.backend.persistance.repository.NotificationRepository;
import pl.tourpol.backend.persistance.repository.UserRepository;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.LongFunction;

import static java.util.Objects.requireNonNull;

public class AccessSensitiveOperationImpl implements AccessSensitiveOperation {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final ContractRepository contractRepository;

    public AccessSensitiveOperationImpl(UserRepository userRepository, NotificationRepository notificationRepository, ContractRepository contractRepository) {
        this.userRepository = requireNonNull(userRepository);
        this.notificationRepository = requireNonNull(notificationRepository);
        this.contractRepository = requireNonNull(contractRepository);
    }

    @Override
    public <T> T callWithAccessCheck(Long id, Callable<T> callable, AccessSensitiveOperationType operationType) {
        return callWithAccessCheck(id, callToFunction(callable), operationType);
    }

    @Override
    public <T> T callWithAccessCheck(Long id, LongFunction<T> callable, AccessSensitiveOperationType operationType) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = user.getUsername();
        AppUser appUser = userRepository.findAppUserByMail(mail)
                .orElseThrow(() -> new AccessDeniedException("User not found"));
        if (appUser.getRole().getRoleName().equals("USER")) {
            checkAccess(appUser, id, operationType);
        }
        return callable.apply(id);
    }
    private void checkAccess(AppUser appUser, Long id, AccessSensitiveOperationType operationType) {
        if (requireNonNull(operationType) == AccessSensitiveOperationType.APP_USER_ACCESS) {
            checkAppUserAccess(id, appUser);
        } else if (operationType == AccessSensitiveOperationType.CONTRACT_ACCESS) {
            checkContractAccess(id, appUser);
        } else if (operationType == AccessSensitiveOperationType.NOTIFICATION_ACCESS) {
            checkNotificationAccess(id, appUser);
        }
    }

    private void checkNotificationAccess(Long id, AppUser appUser) {
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RequestException(RequestErrorMessage.NOTIFICATION_NOT_FOUND));
        if (!appUser.equals(notification.getContract().getUser())) {
            throw new AccessDeniedException("Cannot operate on this notification");
        }
    }

    private void checkContractAccess(Long id, AppUser appUser) {
        var contract = contractRepository.findById(id)
                .orElseThrow(() -> new RequestException(RequestErrorMessage.CONTRACT_NOT_EXISTS));
        if (!appUser.equals(contract.getUser())) {
            throw new AccessDeniedException("Cannot operate on this contract");
        }
    }

    private void checkAppUserAccess(Long id, AppUser appUser) {
        if (!Objects.equals(appUser.getId(), id)) {
            throw new AccessDeniedException("Cannot operate on this user");
        }
    }

    private static <T> LongFunction<T> callToFunction(Callable<T> callable) {
        return id -> {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
