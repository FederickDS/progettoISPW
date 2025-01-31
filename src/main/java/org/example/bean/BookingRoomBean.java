package org.example.bean;

import org.example.facade.ApplicationFacade;
import org.example.view.BookingRoom;

import java.time.LocalDate;
import java.util.logging.Logger;

public class BookingRoomBean {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int participantsNumber;
    private static final Logger logger = Logger.getLogger(BookingRoomBean.class.getName());

    public BookingRoomBean() {
        //vuoto per lui
    }

    public void populateView(BookingRoom bookingRoom) {
        if(!checkCompatibleData(bookingRoom)) {
            return;
        }
        setCheckIn(bookingRoom.getCheckInDate());
        setCheckOut(bookingRoom.getCheckOutDate());
        setParticipantsNumber(bookingRoom.getParticipants());
    }

    public boolean checkCompatibleData(BookingRoom bookingRoom) {
        boolean isCompatible = true;
        int maxNumberOfParticipants = ApplicationFacade.getMaxNumberOfParticipants();
        bookingRoom.hideAllErrors();
        // Recupera le date selezionate (fare in bookRoom)
        var checkInDate = bookingRoom.getCheckInDate();
        var checkOutDate = bookingRoom.getCheckOutDate();
        var numberOfParticipants = bookingRoom.getParticipants();

        if(checkInDate == null){
            bookingRoom.setCheckInError("Scegli una data per il check in");
            isCompatible = false;
            return isCompatible;
        }

        if (checkOutDate == null) {
            bookingRoom.setCheckOutError("Scegli una data per il check out");
            isCompatible = false;
        }

        if(numberOfParticipants <= 0){
            bookingRoom.setParticipantsError("Non puoi mettere un numero negativo di partecipanti");
            isCompatible = false;
        }

        if(numberOfParticipants> maxNumberOfParticipants){
            bookingRoom.setParticipantsError("Il massimo numero di partecipanti e' " + maxNumberOfParticipants);
            isCompatible = false;
        }

        try {
            if (checkInDate.isAfter(checkOutDate)) {
                bookingRoom.setCheckInError("Metti una data precedente al check out");
                isCompatible = false;
            }
        } catch (NullPointerException e) {
            logger.info("testo errato");
        }
        return isCompatible;
    }


    public int getParticipantsNumber() {
        return participantsNumber;
    }
    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }
    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }
    public LocalDate getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
}
