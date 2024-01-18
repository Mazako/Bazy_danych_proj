export interface NotificationDTO {
    id: number;
    content: string;
    seen: boolean;
    sendDate: string;
    tourName: string;
    departureDate: string;
    returnDate: string;
    type: NotificationType;
    reservationDate: string;
    pearsonCount: number;
}

export enum NotificationType {
    ReservationConfirmation = "Reservation Confirmation",
    PaymentReminder = "Payment Reminder",
    TravelAlert = "Travel Alert",
    PostTripSurvey = "Post-Trip Survey"
}
