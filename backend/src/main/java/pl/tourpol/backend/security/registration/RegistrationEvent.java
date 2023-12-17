package pl.tourpol.backend.security.registration;

import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {

    private final String mail;
    private final String token;
    private final String applicationUrl;

    public RegistrationEvent(String mail, String token, String applicationUrl) {
        super(mail);
        this.mail = mail;
        this.token = token;
        this.applicationUrl = applicationUrl;
    }

    public String getMail() {
        return mail;
    }

    public String getToken() {
        return token;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }
}
