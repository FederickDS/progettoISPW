package org.example.bean;

import org.example.facade.ApplicationFacadeInterface;
import org.example.view.BookingRoom;
import org.example.view.BookingRoomAlternative;

import java.time.LocalDate;
import java.util.logging.Logger;

public class BookingRoomBean {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int participantsNumber;
    private int roomNumber;
    private static final Logger logger = Logger.getLogger(BookingRoomBean.class.getName());
    private static final String ERROR_TEXT = "Formato data non valido (YYYY-MM-DD)";

    public BookingRoomBean() {
        //vuoto per lui
    }

    public void populateView(BookingRoom bookingRoom, ApplicationFacadeInterface applicationFacade) {
        if(!checkCompatibleData(bookingRoom, applicationFacade)) {
            return;
        }
        setCheckIn(bookingRoom.getCheckInDate());
        setCheckOut(bookingRoom.getCheckOutDate());
        setParticipantsNumber(bookingRoom.getParticipants());
    }

    public boolean checkCompatibleData(BookingRoom bookingRoom, ApplicationFacadeInterface applicationFacade) {
        boolean isCompatible = true;
        int maxNumberOfParticipants = applicationFacade.getMaxNumberOfParticipants();
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

    public void populateView(BookingRoomAlternative bookingRoom, ApplicationFacadeInterface applicationFacade) {
        if (!checkCompatibleData(bookingRoom, applicationFacade)) {
            return;
        }

        LocalDate checkInDate = null;
        LocalDate checkOutDate = null;

        try {
            checkInDate = LocalDate.parse(bookingRoom.getCheckInDate());
        } catch (Exception e) {
            bookingRoom.setCheckInError(ERROR_TEXT);
            return;
        }

        try {
            checkOutDate = LocalDate.parse(bookingRoom.getCheckOutDate());
        } catch (Exception e) {
            bookingRoom.setCheckOutError(ERROR_TEXT);
            return;
        }
        setCheckIn(checkInDate);
        setCheckOut(checkOutDate);
        setParticipantsNumber(bookingRoom.getParticipants());
    }

    public boolean checkCompatibleData(BookingRoomAlternative bookingRoom, ApplicationFacadeInterface applicationFacade) {
        boolean isCompatible = true;
        int maxNumberOfParticipants = applicationFacade.getMaxNumberOfParticipants();

        bookingRoom.hideAllErrors();

        String checkInDateStr = bookingRoom.getCheckInDate();
        String checkOutDateStr = bookingRoom.getCheckOutDate();
        Integer numberOfParticipants = bookingRoom.getParticipants();

        LocalDate checkInDate = null;
        LocalDate checkOutDate = null;

        try {
            checkInDate = LocalDate.parse(checkInDateStr);
        } catch (Exception e) {
            bookingRoom.setCheckInError(ERROR_TEXT);
            isCompatible = false;
        }

        try {
            checkOutDate = LocalDate.parse(checkOutDateStr);
        } catch (Exception e) {
            bookingRoom.setCheckOutError(ERROR_TEXT);
            isCompatible = false;
        }

        if (checkInDate == null) {
            bookingRoom.setCheckInError("Scegli una data per il check-in");
            isCompatible = false;
            return isCompatible;
        }

        if (checkOutDate == null) {
            bookingRoom.setCheckOutError("Scegli una data per il check-out");
            isCompatible = false;
        }

        if (numberOfParticipants == null || numberOfParticipants <= 0) {
            bookingRoom.setParticipantsError("Numero di partecipanti non valido");
            isCompatible = false;
        }

        if (numberOfParticipants != null && numberOfParticipants > maxNumberOfParticipants) {
            bookingRoom.setParticipantsError("Il massimo numero di partecipanti è " + maxNumberOfParticipants);
            isCompatible = false;
        }

        if (checkOutDate != null && checkInDate.isAfter(checkOutDate)) {
            bookingRoom.setCheckInError("La data di check-in deve essere precedente al check-out");
            isCompatible = false;
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

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
