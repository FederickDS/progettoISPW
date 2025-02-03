package org.example.dao;

import org.example.entity.Reservation;
import org.example.entity.Service;
import org.example.entity.Activity;
import org.example.entity.Client;

import java.sql.SQLException;
import java.util.*;

public class InMemoryReservationDao implements GenericDao<Reservation> {
    private final Map<Integer, Reservation> reservations = new HashMap<>();
    private final Map<Integer, List<Service>> services = new HashMap<>();
    private final Map<Integer, List<Activity>> activities = new HashMap<>();
    private final Map<Integer, List<Client>> clients = new HashMap<>();
    private int currentId = 1;

    @Override
    public void create(Reservation reservation) throws SQLException {
        if (reservation == null) {
            throw new SQLException("La prenotazione non può essere null");
        }

        // Assegna un nuovo ID alla prenotazione
        reservation.setReservationId(currentId++);
        reservations.put(reservation.getReservationId(), reservation);

        // Salva i servizi, le attività e i clienti associati
        services.put(reservation.getReservationId(), new ArrayList<>(reservation.getFreeServices()));
        activities.put(reservation.getReservationId(), new ArrayList<>(reservation.getFreeActivities()));
        clients.put(reservation.getReservationId(), new ArrayList<>(reservation.getClients()));
    }

    @Override
    public Reservation read(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Il metodo read accetta un solo parametro di tipo Integer (reservationId)");
        }
        int reservationId = (Integer) keys[0];
        return reservations.get(reservationId);
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        if (!reservations.containsKey(reservation.getReservationId())) {
            throw new SQLException("La prenotazione con ID " + reservation.getReservationId() + " non esiste.");
        }

        reservations.put(reservation.getReservationId(), reservation);

        // Aggiorna anche i servizi, le attività e i clienti associati
        services.put(reservation.getReservationId(), new ArrayList<>(reservation.getFreeServices()));
        activities.put(reservation.getReservationId(), new ArrayList<>(reservation.getFreeActivities()));
        clients.put(reservation.getReservationId(), new ArrayList<>(reservation.getClients()));
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Il metodo delete accetta un solo parametro di tipo Integer (reservationId)");
        }
        int reservationId = (Integer) keys[0];

        if (!reservations.containsKey(reservationId)) {
            throw new SQLException("La prenotazione con ID " + reservationId + " non esiste.");
        }

        reservations.remove(reservationId);
        services.remove(reservationId);
        activities.remove(reservationId);
        clients.remove(reservationId);
    }

    @Override
    public List<Reservation> readAll() {
        return new ArrayList<>(reservations.values());
    }
}
