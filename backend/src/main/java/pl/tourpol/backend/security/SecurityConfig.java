package pl.tourpol.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.tourpol.backend.persistance.repository.*;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperationImpl;
import pl.tourpol.backend.security.registration.RegistrationListener;
import pl.tourpol.backend.security.registration.RegistrationListenerImpl;
import pl.tourpol.backend.security.registration.RegistrationService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Value("${appMail:tourpol.aplikacja@gmail.com}")
    private String appMail;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtAuthFilter jwtAuthFilter,
                                            AuthenticationProvider authenticationProvider,
                                            CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/public/api/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll())
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(FormLoginConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    JwtService jwtService() {
        return new JwtService();
    }

    @Bean
    JwtAuthFilter jwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        return new JwtAuthFilter(jwtService, userDetailsService);
    }

    @Bean
    RegistrationListener registrationListener(JavaMailSender javaMailSender) {
        return new RegistrationListenerImpl(javaMailSender, appMail);
    }

    @Bean
    RegistrationService registrationService(UserRepository userRepository,
                                            VerificationTokenRepository verificationTokenRepository,
                                            RoleRepository roleRepository,
                                            PasswordEncoder passwordEncoder) {
        return new RegistrationService(userRepository, verificationTokenRepository,
                () -> roleRepository.findById(2L).get(), passwordEncoder);
    }

    @Bean
    AccessSensitiveOperation accessSensitiveOperation(UserRepository userRepository,
                                                      NotificationRepository notificationRepository,
                                                      ContractRepository contractRepository) {
        return new AccessSensitiveOperationImpl(userRepository, notificationRepository, contractRepository);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Specify the origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Specify the HTTP methods
        configuration.setAllowCredentials(true); // Allow credentials
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}