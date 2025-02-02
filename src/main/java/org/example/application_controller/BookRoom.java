package org.example.application_controller;

import org.example.bean.*;
import org.example.facade.ApplicationFacade;
import org.example.factory.ModelBeanFactory;
import org.example.dao.DaoFactory;
import org.example.entity.*;
import org.example.graphic_controller.SessionManager;
import org.example.view.BookingRoom;
import org.example.entity.ReservationStatus;

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
    private Reservation reservation;

    public BookRoom() {
        //il costruttore non deve definire attributi
    }

    public void setServicesToReservation(List<Activity> activities, List<Service> services) {
        this.selectedActivities = activities;
        this.selectedServices = services;
    }

    public boolean checkHours(BookingRoomBean bookingRoomBean) {
        LocalDate checkIn = bookingRoomBean.getCheckIn();
        LocalDate checkOut = bookingRoomBean.getCheckOut();
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

    public int selectRoom(BookingRoomBean bookingRoomBean) {
        LocalDate checkIn = bookingRoomBean.getCheckIn();
        LocalDate checkOut = bookingRoomBean.getCheckOut();
        int participantsNumber = bookingRoomBean.getParticipantsNumber();
        int result = -1;

        // Ottieni tutte le stanze disponibili
        List<Room> rooms = DaoFactory.getRoomDao().readAll();

        // Ottieni tutte le prenotazioni esistenti
        List<Reservation> reservations = DaoFactory.getReservationDao().readAll();

        // Crea una lista di stanze occupate nell'intervallo richiesto
        Set<Integer> occupiedRoomNumbers = new HashSet<>();
        for (Reservation singleReservation : reservations) {
            DailyTimeInterval reservationInterval = singleReservation.getTimetable();
            if (reservationInterval != null && reservationInterval.overlapsWith(checkIn, checkOut)) {
                occupiedRoomNumbers.add(singleReservation.getRoom().getRoomNumber());
            }
        }

        // Cerca una stanza libera con il numero di posti richiesto
        for (Room room : rooms) {
            if (!occupiedRoomNumbers.contains(room.getRoomNumber()) && room.getMaxPeople() == participantsNumber) {
                return room.getRoomNumber(); // Ritorna appena trova una stanza disponibile
            }
        }

        return result; // Se nessuna stanza libera è trovata, restituisce -1
    }


    public void saveReservation(BookingRoomBean bookingRoomBean) {
        LocalDate checkIn = bookingRoomBean.getCheckIn();
        LocalDate checkOut = bookingRoomBean.getCheckOut();
        int roomNumber = bookingRoomBean.getRoomNumber();
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
        //calcolo prezzo
        newReservation.calculatePrice();
        // Salvataggio nel database tramite il DAO
        try {
            //potrebbe non esistere il timetable
            DailyTimeInterval timetable = newReservation.getTimetable();
            DaoFactory.getTimeIntervalDao().create(timetable);//se non esiste, la crea
            DaoFactory.getReservationDao().create(newReservation);
            this.reservation = newReservation;
            ApplicationFacade.sendReservationEmail(newReservation);
        }catch (SQLException e){
            logger.info("prenotazione non riuscita");
        }
    }

    public BeanClientDetails getClientDetails() {
        try {
            ValidateLogin validateLogin = new ValidateLogin();
            validateLogin.validate(ModelBeanFactory.loadLoginBean());
            DaoFactory daoFactory = new DaoFactory();
            Client client = daoFactory.getClientDao().read(ModelBeanFactory.loadLoginBean().getEmail());
            if (client == null) {
                return null;
            }
            return ModelBeanFactory.getBeanClientDetails(client);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<BeanReservationDetails> getReservationForClient(String clientEmail) {
        List<Reservation> allReservations = DaoFactory.getReservationDao().readAll();
        List<BeanReservationDetails> relevantReservations = new ArrayList<>();

        for (Reservation singleReservation : allReservations) {
            if (singleReservation.getClients().stream().anyMatch(client -> client.getEmail().equals(clientEmail))) {
                relevantReservations.add(ModelBeanFactory.getBeanReservationDetails(singleReservation));
            }
        }
        return relevantReservations;
    }

    public BeanClientEReservationDetails getClientAndReceptionistDetails() {
        BeanClientDetails clientDetails = getClientDetails();
        if (clientDetails == null) {
            return null;
        }
        List<BeanReservationDetails> receptionistDetails = getReservationForClient(clientDetails.getEmail());
        return new BeanClientEReservationDetails(clientDetails, receptionistDetails);
    }

    public boolean updateClientDetails(BeanClientDetails clientDetails) {
        try {
            // Recupera il cliente attuale dal database
            DaoFactory daoFactory = new DaoFactory();
            Client existingClient = daoFactory.getClientDao().read(clientDetails.getEmail());
            if (existingClient == null) {
                logger.warning("Cliente non trovato: " + clientDetails.getEmail());
                return false;
            }

            // Aggiorna la password
            if (clientDetails.getPassword() != null && !clientDetails.getPassword().isEmpty()) {
                existingClient.setPassword(clientDetails.getPassword());
            }

            // Salva le modifiche nel database
            daoFactory.getClientDao().update(existingClient);

            // Aggiorna i dati in sessione
            SessionManager.getInstance().setCredentials(
                    clientDetails.getEmail(),
                    clientDetails.getPassword() != null ? clientDetails.getPassword() : SessionManager.getInstance().getPassword(),
                    "client"
            );

            return true;
        } catch (SQLException e) {
            logger.severe("Errore durante l'aggiornamento del cliente: " + e.getMessage());
            return false;
        }
    }

    public List<BeanReservationDetails> getAllReservations() {
        return DaoFactory.getReservationDao().readAll().stream()
                .filter(singleReservation -> singleReservation.getStatus() == ReservationStatus.PENDING)
                .map(ModelBeanFactory::getBeanReservationDetails)
                .toList();
    }

    public boolean confirmReservation(int reservationId) {
        try {
            Reservation singleReservation = DaoFactory.getReservationDao().read(reservationId);
            if (singleReservation == null) {
                return false;
            }
            singleReservation.setStatus(ReservationStatus.BOOKED);
            DaoFactory.getReservationDao().update(singleReservation);
            return true;
        } catch (SQLException e) {
            logger.severe("Errore durante la conferma della prenotazione: " + e.getMessage());
            return false;
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

    public PaymentBean getReservationBean() {
        return ModelBeanFactory.toPaymentBean(this.reservation);
    }
}
