package org.example.application_controller;

import org.example.bean.BookingRoomBean;
import org.example.dao.InMemoryReservationDao;
import org.example.dao.InMemoryRoomDao;
import org.example.entity.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRoomTest {
    private BookRoomTestable bookRoom;
    private InMemoryRoomDao roomDao;
    private InMemoryReservationDao reservationDao;

    @BeforeEach
    void setUp() throws SQLException {
        roomDao = new InMemoryRoomDao();
        reservationDao = new InMemoryReservationDao();
        bookRoom = new BookRoomTestable(roomDao, reservationDao); // Usiamo la versione testabile

        // Creiamo stanze disponibili nell'hotel
        for (int i = 101; i <= 105; i++) {
            Room room = new Room();
            room.setRoomNumber(i);
            room.setMaxPeople(2);
            roomDao.create(room);
        }
    }

    /**
     * ✅ Verifica che una prenotazione valida venga confermata correttamente
     */
    @Test
    void testSuccessfulBooking() throws SQLException {
        BookingRoomBean bookingRoomBean = new BookingRoomBean();
        bookingRoomBean.setCheckIn(LocalDate.of(2026, 3, 10));
        bookingRoomBean.setCheckOut(LocalDate.of(2026, 3, 15));
        bookingRoomBean.setParticipantsNumber(2);

        int selectedRoom = bookRoom.selectRoom(bookingRoomBean);
        assertNotEquals(-1, selectedRoom, "❌ ERRORE: Nessuna stanza disponibile per una prenotazione valida!");

        bookingRoomBean.setRoomNumber(selectedRoom);
        bookRoom.saveReservation(bookingRoomBean);

        // Verifichiamo che la prenotazione sia effettivamente salvata
        List<Reservation> allReservations = reservationDao.readAll();
        assertTrue(allReservations.stream()
                        .anyMatch(res -> res.getRoom().getRoomNumber() == selectedRoom),
                "❌ ERRORE: La stanza " + selectedRoom + " non è stata prenotata!");
    }

    /**
     * ❌ Verifica che non sia possibile prenotare una stanza già occupata
     */
    @Test
    void testOverbooking() throws SQLException {
        // Creiamo una prenotazione iniziale per occupare una stanza
        BookingRoomBean bookingRoomBean1 = new BookingRoomBean();
        bookingRoomBean1.setCheckIn(LocalDate.of(2026, 3, 10));
        bookingRoomBean1.setCheckOut(LocalDate.of(2026, 3, 15));
        bookingRoomBean1.setParticipantsNumber(2);

        int firstRoom = bookRoom.selectRoom(bookingRoomBean1);
        assertNotEquals(-1, firstRoom, "❌ ERRORE: Nessuna stanza disponibile al primo tentativo!");

        bookingRoomBean1.setRoomNumber(firstRoom);
        bookRoom.saveReservation(bookingRoomBean1);

        // Creiamo un'altra prenotazione per lo stesso periodo
        BookingRoomBean bookingRoomBean2 = new BookingRoomBean();
        bookingRoomBean2.setCheckIn(LocalDate.of(2026, 3, 12));
        bookingRoomBean2.setCheckOut(LocalDate.of(2026, 3, 16));
        bookingRoomBean2.setParticipantsNumber(2);

        int secondRoom = bookRoom.selectRoom(bookingRoomBean2);
        assertNotEquals(firstRoom, secondRoom, "❌ ERRORE: La stessa stanza è stata prenotata due volte!");
    }

    /**
     * 🚫 Verifica che non sia possibile prenotare con date non valide (check-out prima del check-in)
     */
    @Test
    void testInvalidDates() {
        BookingRoomBean bookingRoomBean = new BookingRoomBean();
        bookingRoomBean.setCheckIn(LocalDate.of(2026, 3, 15));
        bookingRoomBean.setCheckOut(LocalDate.of(2026, 3, 10)); // ❌ Data non valida!

        boolean result = bookRoom.checkHours(bookingRoomBean);
        assertFalse(result, "❌ ERRORE: Il sistema ha accettato date non valide!");
    }

    /**
     * 🔄 Test di stress: riempie tutte le stanze e verifica che alla fine non ci siano più stanze disponibili
     */
    @Test
    void testOverbookingLoop() throws SQLException {
        boolean foundNoAvailability = false;

        for (int i = 1; i <= 10; i++) {
            BookingRoomBean bookingRoomBean = new BookingRoomBean();
            bookingRoomBean.setCheckIn(LocalDate.of(2026, 3, 12));
            bookingRoomBean.setCheckOut(LocalDate.of(2026, 3, 16));
            bookingRoomBean.setParticipantsNumber(2);

            int selectedRoom = bookRoom.selectRoom(bookingRoomBean);
            System.out.println("🔍 Tentativo " + i + " → Stanza selezionata: " + selectedRoom);

            if (selectedRoom == -1) {
                foundNoAvailability = true;
                System.out.println("❌ Nessuna stanza disponibile al tentativo " + i);
                break;
            }

            bookingRoomBean.setRoomNumber(selectedRoom);
            bookRoom.saveReservation(bookingRoomBean);

            List<Reservation> allReservations = reservationDao.readAll();
            assertTrue(allReservations.stream()
                            .anyMatch(res -> res.getRoom().getRoomNumber() == selectedRoom),
                    "❌ ERRORE: La stanza " + selectedRoom + " non risulta occupata dopo la prenotazione!");
        }

        System.out.println("📌 Numero totale di prenotazioni salvate: " + reservationDao.readAll().size());
        assertTrue(foundNoAvailability, "❌ ERRORE: Il test deve confermare che almeno una volta non c'è disponibilità.");
    }
}
