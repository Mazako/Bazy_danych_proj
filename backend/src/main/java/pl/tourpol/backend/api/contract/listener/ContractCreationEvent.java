package pl.tourpol.backend.api.contract.listener;

import java.time.LocalDate;

public class ContractCreationEvent extends ContractEvent{
    private final LocalDate reservationDate;

    public ContractCreationEvent(String mail, String name, float price, String resortName) {
        super(mail, name, price, resortName);
        this.reservationDate = LocalDate.now();
    }

    @Override
    public LocalDate getReservationDate() {
        return reservationDate;
    }
}
