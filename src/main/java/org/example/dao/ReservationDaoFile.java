package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.entity.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoFile implements GenericDao<Reservation> {
    private static final String FILE_PATH = "reservations.json";
    private final Gson gson;
    private List<Reservation> reservations;

    public ReservationDaoFile() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registra l'adapter
                .setPrettyPrinting()
                .create();
        reservations = loadFromFile();
    }

    private List<Reservation> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Reservation>>() {}.getType();
            List<Reservation> loadedReservations = gson.fromJson(reader, listType);
            return loadedReservations != null ? loadedReservations : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(reservations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Reservation reservation) {
        if (read(reservation.getReservationId()) != null) {
            throw new IllegalArgumentException("Reservation already exists: " + reservation.getReservationId());
        }
        reservations.add(reservation);
        saveToFile();
    }

    @Override
    public Reservation read(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Invalid keys for reading Reservation.");
        }
        int reservationId = (Integer) keys[0];

        return reservations.stream()
                .filter(reservation -> reservation.getReservationId() == reservationId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Reservation reservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == reservation.getReservationId()) {
                reservations.set(i, reservation);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Reservation not found: " + reservation.getReservationId());
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Invalid keys for deleting Reservation.");
        }
        int reservationId = (Integer) keys[0];

        reservations.removeIf(reservation -> reservation.getReservationId() == reservationId);
        saveToFile();
    }

    @Override
    public List<Reservation> readAll() {
        return new ArrayList<>(reservations);
    }
}
