package org.example.entity;

public enum ReservationStatus {
    PENDING,  // Prenotazione effettuata ma non ancora pagata
    BOOKED,  // Prenotazione pagata e confermata
    CHECKED_IN,
    CHECKED_OUT,
    CANCELLED
}