package pl.tourpol.backend.api.contract.listener;

import java.time.LocalDate;

public class ContractRefundEvent extends ContractEvent {
    public ContractRefundEvent(String mail, String name, float price, String resortName) {
        super(mail, name, price, resortName);
    }

    @Override
    public LocalDate getReservationDate() {
        return null;
    }
}
