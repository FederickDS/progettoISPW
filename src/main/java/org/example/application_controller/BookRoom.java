package org.example.application_controller;

import org.example.dao.DaoFactory;
import org.example.entity.*;
import org.example.view.BookingRoom;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class BookRoom {
    private List<Client> clientList = new ArrayList<>();
    private List<Activity> selectedActivities = new ArrayList<>();
    private List<Service> selectedServices = new ArrayList<>();
    private static final String CHECK_INTERVALS = "opening";
    private static final int MAX_NUMBER_OF_PARTICIPANTS = 5;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public BookRoom() {
        //il costruttore non deve definire attributi
    }

    public void setServicesToReservation(List<Activity> activities, List<Service> services) {
        this.selectedActivities = activities;
        this.selectedServices = services;
    }

    public boolean checkHours(LocalDate checkIn, LocalDate checkOut) {
        // Ottieni gli intervalli di tempo relativi ai "giorni di apertura"
        List<DailyTimeInterval> availableIntervals = DaoFactory.getTimeIntervalDao().readAll().stream()
                .filter(interval -> CHECK_INTERVALS.equals(interval.getType())) // Filtro per "giorni di apertura"
                .toList();

        // Controlla se almeno un intervallo include il periodo richiesto
        for (DailyTimeInterval interval : availableIntervals) {
            if (interval.isAvailable(checkIn, checkOut)) {
                return true; // Se l'intervallo è valido, ritorna subito true
            }
        }

        return false; // Nessun intervallo valido trovato
    }

    public int selectRoom(LocalDate checkIn, LocalDate checkOut, int participantsNumber) {
        int result = -1;

        // Ottieni tutte le stanze disponibili
        List<Room> rooms = DaoFactory.getRoomDao().readAll();

        // Ottieni tutte le prenotazioni esistenti
        List<Reservation> reservations = DaoFactory.getReservationDao().readAll();

        // Crea una lista di stanze occupate nell'intervallo richiesto
        Set<Room> occupiedRooms = new HashSet<>();
        for (Reservation reservation : reservations) {
            DailyTimeInterval reservationInterval = reservation.getTimetable();
            if (!reservationInterval.isAvailable(checkIn, checkOut)) { // La stanza è occupata in questo intervallo
                occupiedRooms.add(reservation.getRoom());
            }
        }

        // Cerca una stanza libera con il numero di posti richiesto
        for (Room room : rooms) {
            if (!occupiedRooms.contains(room) && room.getMaxPeople() == participantsNumber) {

                return room.getRoomNumber(); // Appena ne troviamo una, restituiamo il numero della stanza
            }
        }

        return result; // Se nessuna stanza libera è trovata, restituisce -1
    }


    public boolean checkCompatibleData(BookingRoom bookingRoom) {
        boolean isCompatible = true;
        bookingRoom.hideAllErrors();
        // Recupera le date selezionate (fare in bookRoom)
        var checkInDate = bookingRoom.getCheckInDate();
        var checkOutDate = bookingRoom.getCheckOutDate();
        var numberOfParticipants = bookingRoom.getParticipants();

        if(checkInDate == null){
            bookingRoom.setCheckInError("Scegli una data per il check in");
            isCompatible = false;
        }

        if (checkOutDate == null) {
            bookingRoom.setCheckOutError("Scegli una data per il check out");
            isCompatible = false;
        }

        if(numberOfParticipants <= 0){
            bookingRoom.setParticipantsError("Non puoi mettere un numero negativo di partecipanti");
            isCompatible = false;
        }

        if(numberOfParticipants>= MAX_NUMBER_OF_PARTICIPANTS){
            bookingRoom.setParticipantsError("Il massimo numero di partecipanti e' " + MAX_NUMBER_OF_PARTICIPANTS);
            isCompatible = false;
        }

        if (checkInDate.isAfter(checkOutDate)) {
            bookingRoom.setCheckInError("Metti una data precedente al check out");
            isCompatible = false;
        }
        return isCompatible;
    }

    public void saveReservation(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        // Creazione dell'intervallo di tempo della prenotazione
        DailyTimeInterval reservationInterval = new DailyTimeInterval(checkIn, checkOut, "reservation");

        // Creazione dell'oggetto Reservation
        Reservation newReservation = new Reservation();
        newReservation.setTimetable(reservationInterval);
        newReservation.setClients(clientList);
        newReservation.setFreeActivities(selectedActivities);
        newReservation.setFreeServices(selectedServices);

        // Recupero dell'oggetto Room corrispondente
        try {
            Room room = DaoFactory.getRoomDao().read(roomNumber);
            newReservation.setRoom(room);
        }catch (SQLException e){
            logger.info("data invalida");
        }
        newReservation.printReservationDetails();
        // Salvataggio nel database tramite il DAO
        try {
            DaoFactory.getReservationDao().create(newReservation);
        }catch (SQLException e){
            logger.info("prenotazione non riuscita");
        }
    }


    public Client getFirstClient() {
        if (clientList.isEmpty()) {
            return null;
        }
        return clientList.get(0);
    }

    public void addFirstClient(Client client) {
        clientList.add(client);
    }

    public void addClient(Client client) {
        //AGGIUNGI LOGICA PER EVITARE INSERIMENTO INAPPROPRIATO O MULTIPLO
        clientList.add(client);
    }

    public List<Activity> getAvailableActivities() {
        return DaoFactory.getAvailableActivities();
    }

    public List<Service> getAvailableServices() {
        return DaoFactory.getAvailableServices();
    }

    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }
}
