package pl.tourpol.backend.api.contract.listener;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import static java.util.Objects.requireNonNull;

public class ContractCreationListenerImpl implements ContractCreationListener{

    private final JavaMailSender javaMailSender;
    private final String appMail;
    private final String bankAccount;

    public ContractCreationListenerImpl(JavaMailSender javaMailSender, String appMail, String bankAccount) {
        this.javaMailSender = requireNonNull(javaMailSender);
        this.appMail = requireNonNull(appMail);
        this.bankAccount = requireNonNull(bankAccount);
    }

    @Override
    @Async
    public void onApplicationEvent(ContractEvent event) {
        if (event instanceof ContractCreationEvent) {
            sendCreationMessage(event);
        } else {
            sendRemovalEvent(event);
        }
    }

    private void sendCreationMessage(ContractEvent event) {
        var helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), "utf-8");
        String htmlMessage = String.format("""
                <h1>Witaj, %s</h1>
                <h2>Pomyślnie zarezerwowano wycieczkę do: %s </h2>
                <p>Zapłać za wycieczkę do %s, wykonując przelew na %.2f PLN na numer konta: %s</p>
                """,
                event.getName(),
                event.getResortName(),
                event.getReservationDate().plusWeeks(1),
                event.getPrice(),
                bankAccount);
        try {
            helper.setFrom(appMail);
            helper.setTo(event.getMail());
            helper.setSubject("Tourpol - potwierdzenie rezerwacji");
            helper.setText(htmlMessage, true);
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRemovalEvent(ContractEvent event) {
        var helper = new MimeMessageHelper(javaMailSender.createMimeMessage(), "utf-8");
        String htmlMessage = String.format("""
                <h1>Witaj, %s</h1>
                <h2>Odnotowaliśmy Twoje anulowanie wycieczki do: %s </h2>
                <p>Zwrócimy kwotę %.2f PLN w ciągu dwóch tygodni na numer konta, z którego odnotowaliśmy wpłatę</p>
                """,
                event.getName(),
                event.getResortName(),
                event.getPrice());
        try {
            helper.setFrom(appMail);
            helper.setTo(event.getMail());
            helper.setSubject("Tourpol - potwierdzenie rezerwacji");
            helper.setText(htmlMessage, true);
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
