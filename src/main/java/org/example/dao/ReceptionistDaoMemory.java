package org.example.dao;

import org.example.entity.Receptionist;
import org.example.entity.User;
import org.example.exception.UserAlreadyInsertedException;
import org.example.exception.WrongLoginCredentialsException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDaoMemory implements GenericDao<Receptionist> {
    private final List<Receptionist> storage = new ArrayList<>();

    public ReceptionistDaoMemory() {
        // Aggiunta di receptionist predefiniti
        storage.add(new Receptionist("Admin", "User", "admin@hotel.com", "1234567890",  User.hashWithSHA256("admin123")));
        storage.add(new Receptionist("John", "Doe", "john.doe@hotel.com", "0987654321", User.hashWithSHA256("password")));
        storage.add(new Receptionist("Jane", "Smith", "jane.smith@hotel.com", "1122334455", User.hashWithSHA256("securepass")));
    }

    @Override
    public void create(Receptionist receptionist) {
        if (receptionist == null) throw new IllegalArgumentException("Receptionist non può essere null");
        if (!isEmailUnique(receptionist.getEmail())) {
            throw new UserAlreadyInsertedException("Esiste già un receptionist con questa email");
        }
        storage.add(receptionist);
    }

    @Override
    public Receptionist read(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Devi fornire un solo parametro di tipo String (email).");
        }
        String email = (String) keys[0];

        return storage.stream()
                .filter(receptionist -> receptionist.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new WrongLoginCredentialsException("Credenziali non corrette"));
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
