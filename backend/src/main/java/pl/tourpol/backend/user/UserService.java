package pl.tourpol.backend.user;

import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.repository.UserRepository;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;

import java.util.Optional;

import static pl.tourpol.backend.security.permissions.AccessSensitiveOperationType.APP_USER_ACCESS;

public class UserService {
    private final UserRepository userRepository;

    private final AccessSensitiveOperation accessSensitiveOperation;

    public UserService(UserRepository userRepository, AccessSensitiveOperation accessSensitiveOperation) {
        this.userRepository = userRepository;
        this.accessSensitiveOperation = accessSensitiveOperation;
    }

    public boolean deleteUserById(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public Optional<AppUser> getUserByEmail(String mail) {
        return userRepository.findAppUserByMail(mail);
    }

    public Optional<AppUser> findUserByName(String firstName) {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getName().equals(firstName))
                .findFirst()
                .map(u -> accessSensitiveOperation.callWithAccessCheck(u.getId(), () -> u, APP_USER_ACCESS));
    }
}
