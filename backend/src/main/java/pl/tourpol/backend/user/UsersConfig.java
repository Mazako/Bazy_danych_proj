package pl.tourpol.backend.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tourpol.backend.persistance.repository.UserRepository;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;

@Configuration
class UsersConfig {

    @Bean
    UserService userService(UserRepository userRepository, AccessSensitiveOperation accessSensitiveOperation) {
        return new UserService(userRepository, accessSensitiveOperation);
    }
}
