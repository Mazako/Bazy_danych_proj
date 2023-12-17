package pl.tourpol.backend.schedulers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tourpol.backend.persistance.repository.VerificationTokenRepository;

@Configuration
class SchedulerConfig {

    @Bean
    SchedulerInvoker schedulerInvoker(VerificationTokenRepository verificationTokenRepository) {
        return new SchedulerInvoker(verificationTokenRepository);
    }
}
