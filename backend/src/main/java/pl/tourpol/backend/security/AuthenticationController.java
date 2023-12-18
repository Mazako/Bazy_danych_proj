package pl.tourpol.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.tourpol.backend.security.registration.*;

@RestController
@RequestMapping("/auth")
class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService, RegistrationService registrationService, ApplicationEventPublisher eventPublisher) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registrationService = registrationService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody CredentialsDTO credentials) {
        try {
            var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.mail, credentials.password));
            if (auth.isAuthenticated()) {
                String token = jwtService.generateToken(credentials.mail);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.badRequest().body("Chuj nie logowanie");
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Chuj nie logowanie");
        }
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto,
                               HttpServletRequest request) {
        try {
            String token = registrationService.registerUser(registrationDto);
            String url = "http://" + request.getLocalAddr() + ":" + request.getServerPort();
            eventPublisher.publishEvent(new RegistrationEvent(registrationDto.mail(), token, url));
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirmRegistration/{token}")
    ResponseEntity<?> confirmRegistration(@PathVariable String token) {
        try {
            registrationService.confirmRegistration(token);
            return ResponseEntity.ok().build();
        } catch (RegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    record CredentialsDTO(String mail, String password) {

    }
}