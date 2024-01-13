package pl.tourpol.backend.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import pl.tourpol.backend.persistance.repository.VerificationTokenRepository;

import static java.util.Objects.requireNonNull;

class SchedulerInvoker {
    private final VerificationTokenRepository verificationTokenRepository;

    public SchedulerInvoker(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = requireNonNull(verificationTokenRepository);
    }

    @Scheduled(cron = "0 0 0 * * *")
    void removeExpiredTokens() {
        verificationTokenRepository.removeExpiredTokens();
    }
}
