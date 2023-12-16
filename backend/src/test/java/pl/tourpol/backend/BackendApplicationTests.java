package pl.tourpol.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.tourpol.backend.app.BackendApplication;

@SpringBootTest(classes = BackendApplication.class)
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
