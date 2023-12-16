package pl.tourpol.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    record CredentialsDTO(String mail, String password) {

    }
}
