package pl.tourpol.backend.api.notification;

public enum NotificationTypeValue {
    RESERVATION_CONFIRM(1L),
    PAYMENT_REMAINDER(2L),
    TRAVEL_ALERT(3L),
    POST_TRIP_SURVEY(4L);
    private final long id;

    NotificationTypeValue(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}
