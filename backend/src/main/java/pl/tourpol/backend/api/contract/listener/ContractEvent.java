package pl.tourpol.backend.api.contract.listener;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

public abstract class ContractEvent extends ApplicationEvent {
    private final String mail;
    private final float price;
    private final String resortName;
    private final String name;
    public ContractEvent(String mail, String name, float price, String resortName) {
        super(mail);
        this.mail = mail;
        this.price = price;
        this.resortName = resortName;
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public float getPrice() {
        return price;
    }

    public String getResortName() {
        return resortName;
    }

    public abstract LocalDate getReservationDate();

    public String getName() {
        return name;
    }
}
