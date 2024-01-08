package pl.tourpol.backend.security.registration;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.tourpol.backend.persistance.entity.AppUser;
import pl.tourpol.backend.persistance.entity.Role;
import pl.tourpol.backend.persistance.entity.VerificationToken;
import pl.tourpol.backend.persistance.repository.UserRepository;
import pl.tourpol.backend.persistance.repository.VerificationTokenRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class RegistrationService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final Supplier<Role> userRoleProvider;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, Supplier<Role> userRoleProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRoleProvider = userRoleProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegistrationDto registrationDto) {
        Optional<AppUser> appUserByMail = userRepository.findAppUserByMail(registrationDto.mail());
        if (appUserByMail.filter(AppUser::isEnabled).isPresent()) {
            throw new UserAlreadyExistsException("User with given mail: " + registrationDto.mail() + ", already exists");
        }

        var savedUser = appUserByMail.orElse(userRepository.save(new AppUser(
                registrationDto.firstName(),
                registrationDto.lastName(),
                registrationDto.mail(),
                passwordEncoder.encode(registrationDto.password()),
                userRoleProvider.get(),
                LocalDate.now(),
                registrationDto.phone(),
                false)));

        var token = UUID.randomUUID().toString();
        var verificationToken = new VerificationToken(token, Instant.now().plus(24, ChronoUnit.HOURS), savedUser);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void confirmRegistration(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findTokenByName(token)
                .orElseThrow(() -> new RegistrationException("Could not find token"));

        if (Instant.now().isAfter(verificationToken.getExpirationDate())) {
            throw new RegistrationException("Verification time expired");
        }

        AppUser user = verificationToken.getUser();
        user.setEnabled(true);
        verificationTokenRepository.delete(verificationToken);
        userRepository.save(user);

    }

}
