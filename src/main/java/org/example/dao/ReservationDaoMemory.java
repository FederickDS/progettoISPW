package org.example.dao;

import org.example.entity.Reservation;
import org.example.entity.Service;
import org.example.entity.Activity;
import org.example.entity.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDaoMemory implements GenericDao<Reservation> {
    private final Map<Integer, Reservation> reservations = new HashMap<>();
    private final Map<Integer, List<Service>> services = new HashMap<>();
    private final Map<Integer, List<Activity>> activities = new HashMap<>();
    private final Map<Integer, List<Client>> clients = new HashMap<>();
    private int currentId = 1;

    @Override
    public void create(Reservation reservation) {
        // Assegna un ID alla prenotazione
        reservation.setReservationId(currentId++);
        reservations.put(reservation.getReservationId(), reservation);

        // Salva i servizi, le attività e i clienti
        services.put(reservation.getReservationId(), reservation.getFreeServices());
        activities.put(reservation.getReservationId(), reservation.getFreeActivities());
        clients.put(reservation.getReservationId(), reservation.getClients());
    }

    @Override
    public Reservation read(Object... keys) {
        int reservationId = (int) keys[0];
        return reservations.get(reservationId);
    }

    @Override
    public void update(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);

        // Aggiorna anche i servizi, le attività e i clienti
        services.put(reservation.getReservationId(), reservation.getFreeServices());
        activities.put(reservation.getReservationId(), reservation.getFreeActivities());
        clients.put(reservation.getReservationId(), reservation.getClients());
    }

    @Override
    public void delete(Object... keys) {
        int reservationId = (int) keys[0];
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
