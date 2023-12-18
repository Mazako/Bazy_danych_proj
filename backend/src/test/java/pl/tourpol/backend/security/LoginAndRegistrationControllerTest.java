package pl.tourpol.backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.tourpol.backend.BasicDbTest;
import pl.tourpol.backend.persistance.repository.UserRepository;
import pl.tourpol.backend.security.AuthenticationController.CredentialsDTO;
import pl.tourpol.backend.security.registration.RegistrationDto;
import pl.tourpol.backend.security.registration.RegistrationService;

import static org.assertj.core.api.Assertions.assertThat;

class LoginAndRegistrationControllerTest extends BasicDbTest {

    @SpyBean
    private RegistrationService registrationService;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserRepository userRepository;
    @LocalServerPort
    private int serverPort;
    private String currentToken;

    @BeforeEach
    public void setupTest() {
        Mockito.doAnswer(i -> {
                    String token = (String) i.callRealMethod();
                    currentToken = token;
                    return token;
                }).when(registrationService)
                .registerUser(Mockito.any());
        userRepository.deleteAll();
        currentToken = null;
    }

    @Test
    void shouldProperlyRegisterAndAssignToken() {
        webTestClient.post()
                .uri(String.format("http://localhost:%d/auth/register", serverPort))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new RegistrationDto("John",
                        "Doe",
                        "kaszanka@paruwa.com",
                        "kaszanka",
                        "112 997 998"))
                .exchange()
                .expectStatus()
                .isOk();
        assertThat(currentToken).isNotNull();
    }


    @Test
    void shouldNotAllowToRegisterForTwoSameMails() {
        webTestClient.post()
                .uri(String.format("http://localhost:%d/auth/register", serverPort))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new RegistrationDto("John",
                        "Doe",
                        "kaszanka@paruwa.com",
                        "kaszanka",
                        "112 997 998"))
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.post()
                .uri(String.format("http://localhost:%d/auth/register", serverPort))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new RegistrationDto("Jaś",
                        "Kowalski",
                        "kaszanka@paruwa.com",
                        "kaszanka",
                        "142 937 928"))
                .exchange()
                .expectStatus()
                .is4xxClientError();

        assertThat(currentToken).isNotNull();
    }

    @Test
    void shouldAllowToRegisterAndLoginAfterRegistrationConfirmation() {
        webTestClient.post()
                .uri(String.format("http://localhost:%d/auth/register", serverPort))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new RegistrationDto("John",
                        "Doe",
                        "kaszanka@paruwa.com",
                        "kaszanka",
                        "112 997 998"))
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri(String.format("http://localhost:%d/auth/confirmRegistration/%s", serverPort, currentToken))
                .exchange()
                .expectStatus()
                .isOk();


        String token = webTestClient.post()
                .uri(String.format("http://localhost:%d/auth/login", serverPort))
                .bodyValue(new CredentialsDTO("kaszanka@paruwa.com", "kaszanka"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(token).isNotNull();

    }


}
