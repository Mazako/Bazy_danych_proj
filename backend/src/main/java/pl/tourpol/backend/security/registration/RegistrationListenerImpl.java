package pl.tourpol.backend.security.registration;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import static java.util.Objects.requireNonNull;

public class RegistrationListenerImpl implements RegistrationListener {
    private final JavaMailSender javaMailSender;
    private final String appMail;
    public RegistrationListenerImpl(JavaMailSender javaMailSender, String appMail) {
        this.javaMailSender = requireNonNull(javaMailSender);
        this.appMail = requireNonNull(appMail);
    }

    @Override
    @Async
    public void onApplicationEvent(RegistrationEvent event) {
        MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), "utf-8");
        String htmlMessage = "<h1>Witamy w biurze podróży Tourpol</h1>" +
                String.format("<p>Kliknij <a href=\"%s\">tutaj</a>, aby potwierdzić rejestrację w serwisie</p>",
                        event.getApplicationUrl() + "/auth/confirmRegistration/" + event.getToken());
        try {
            helper.setFrom(appMail);
            helper.setTo(event.getMail());
            helper.setSubject("Tourpol - potwierdzenie rejestracji");
            helper.setText(htmlMessage, true);
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
