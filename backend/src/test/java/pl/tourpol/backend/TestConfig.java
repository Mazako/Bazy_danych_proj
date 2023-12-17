package pl.tourpol.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import pl.tourpol.backend.security.registration.RegistrationEvent;
import pl.tourpol.backend.security.registration.RegistrationListener;

@TestConfiguration
public class TestConfig {

    private static Logger logger = LoggerFactory.getLogger(TestConfig.class);
    @Bean
    public RegistrationListener testRegistrationListener() {
        return event -> logger.info("Registration token: {}", event.getToken());
    }
}
