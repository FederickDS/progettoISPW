package org.example.application_controller;

import org.example.bean.BookingRoomBean;
import org.example.dao.GenericDao;
import org.example.entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class BookRoomTestable extends BookRoom {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public BookRoomTestable(GenericDao<Room> roomDao, GenericDao<Reservation> reservationDao) {
        super(roomDao, reservationDao);
    }

    @Override
    public void saveReservation(BookingRoomBean bookingRoomBean) {
        LocalDate checkIn = bookingRoomBean.getCheckIn();
        LocalDate checkOut = bookingRoomBean.getCheckOut();
        int roomNumber = bookingRoomBean.getRoomNumber();

        DailyTimeInterval reservationInterval = new DailyTimeInterval(checkIn, checkOut, "reservation");

        Reservation newReservation = new Reservation();
        newReservation.setTimetable(reservationInterval);
        newReservation.setClients(getClientList());
        newReservation.setFreeActivities(getSelectedActivities());
        newReservation.setFreeServices(getSelectedServices());

        try {
            Room room = roomDao.read(roomNumber);
            if (room == null) {
                logger.info("Stanza non trovata!");
                return;
            }
            newReservation.setRoom(room);
        } catch (SQLException e) {
            logger.info("Errore nel recupero della stanza");
        }

        if (newReservation.getClients().isEmpty()) {
            Client testClient = new Client();
            testClient.setEmail("test@example.com");
            testClient.setFirstName("Test");
            testClient.setLastName("User");
            newReservation.setClients(List.of(testClient));
        }

        newReservation.calculatePrice();

        try {
            reservationDao.create(newReservation);
            this.reservation = newReservation;
        } catch (SQLException e) {
            logger.info("Prenotazione non riuscita");
        }
    }

    @Override
    public int selectRoom(BookingRoomBean bookingRoomBean) {
        LocalDate checkIn = bookingRoomBean.getCheckIn();
        LocalDate checkOut = bookingRoomBean.getCheckOut();
        int participantsNumber = bookingRoomBean.getParticipantsNumber();

        List<Room> rooms = roomDao.readAll();
        List<Reservation> reservations = reservationDao.readAll();

        if (rooms.isEmpty()) {
            logger.warning("Nessuna stanza trovata nel sistema!");
            return -1;
        }

        Set<Integer> occupiedRoomNumbers = new HashSet<>();
        for (Reservation singleReservation : reservations) {
            DailyTimeInterval reservationInterval = singleReservation.getTimetable();
            if (reservationInterval != null && reservationInterval.overlapsWith(checkIn, checkOut)) {
                occupiedRoomNumbers.add(singleReservation.getRoom().getRoomNumber());
            }
        }

        for (Room room : rooms) {
            if (!occupiedRoomNumbers.contains(room.getRoomNumber()) && room.getMaxPeople() == participantsNumber) {
                return room.getRoomNumber();
            }
        }

        return -1;
    }
    public List<Client> getClientList() {
        return clientList;
    }
}
