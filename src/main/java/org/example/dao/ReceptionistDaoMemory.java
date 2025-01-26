package org.example.dao;

import org.example.entity.Receptionist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDaoMemory implements GenericDao<Receptionist> {
    private final List<Receptionist> storage = new ArrayList<>();
    private static final String MESSAGE = "Receptionist not found";

    @Override
    public void create(Receptionist receptionist) throws SQLException {
        if (receptionist == null) throw new SQLException("Receptionist cannot be null");
        if (!isEmailUnique(receptionist.getEmail())) {
            throw new SQLException("Receptionist with this email already exists");
        }
        storage.add(receptionist);
    }

    @Override
    public Receptionist read(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Devi fornire un solo parametro di tipo String (email).");
        }
        String email = (String) keys[0];

        return storage.stream()
                .filter(receptionist -> receptionist.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new SQLException(MESSAGE));
    }

    @Override
    public void update(Receptionist receptionist) throws SQLException {
        if (receptionist == null) throw new SQLException("Receptionist cannot be null");
        Receptionist existingReceptionist = read(receptionist.getEmail()); // Verifica se esiste già
        storage.remove(existingReceptionist); // Rimuovi il vecchio record
        storage.add(receptionist); // Aggiungi il record aggiornato
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Devi fornire un solo parametro di tipo String (email).");
        }
        String email = (String) keys[0];

        Receptionist existingReceptionist = read(email); // Verifica se esiste già
        storage.remove(existingReceptionist);
    }

    private boolean isEmailUnique(String email) {
        return storage.stream().noneMatch(receptionist -> receptionist.getEmail().equals(email));
    }

    @Override
    public List<Receptionist> readAll() {
        return new ArrayList<>(storage); // Restituisce una copia della lista
    }
}
