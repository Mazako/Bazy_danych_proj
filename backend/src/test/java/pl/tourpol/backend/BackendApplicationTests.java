package pl.tourpol.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(classes = BackendApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

    @Autowired
    private JavaMailSender sender;

    @Test
    void shouldSendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("tourpol.aplikacja@gmail.com");
        simpleMailMessage.setTo("michal.maziarz12@gmail.com");
        simpleMailMessage.setText("No co tam halo");
        sender.send(simpleMailMessage);
    }

}
