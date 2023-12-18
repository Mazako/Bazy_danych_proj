package pl.tourpol.backend.security.permissions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.repository.UserRepository;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.LongFunction;

public class AccessSensitiveOperationImpl implements AccessSensitiveOperation {

    private final UserRepository userRepository;

    public AccessSensitiveOperationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        if (Objects.requireNonNull(operationType) == AccessSensitiveOperationType.APP_USER_ACCESS) {
            checkAppUserAccess(id, appUser);
        } else if (operationType == AccessSensitiveOperationType.CONTRACT_ACCESS) {
            checkContractAccess(id, appUser);
        }
    }

    private void checkContractAccess(Long id, AppUser appUser) {

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
