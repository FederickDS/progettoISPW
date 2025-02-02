package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.entity.Client;
import org.example.exception.UserAlreadyInsertedException;
import org.example.exception.WrongLoginCredentialsException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoMemory implements GenericDao<Client> {
    private final ObservableList<Client> storage;
    private static final String CLIENT_CANNOT_BE_NULL = "Client cannot be null";

    public ClientDaoMemory() {
        storage = FXCollections.observableArrayList();
    }

    @Override
    public void create(Client client) throws SQLException {
        if (client == null) throw new SQLException(CLIENT_CANNOT_BE_NULL);
        if (!isEmailUnique(client.getEmail())) {
            throw new UserAlreadyInsertedException("Esiste già il cliente con questa email");
        }
        if (!isPhoneNumberUnique(client.getPhoneNumber())) {
            throw new UserAlreadyInsertedException("Esiste già il cliente con questo numero di telefono");
        }
        storage.add(client);
    }

    @Override
    public Client read(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Chiave non valida per la ricerca del Client");
        }

        String email = (String) keys[0];
        return storage.stream()
                .filter(client -> client.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new WrongLoginCredentialsException("Credenziali non corrette"));
    }

    @Override
    public void update(Client client) throws SQLException {
        if (client == null) throw new SQLException(CLIENT_CANNOT_BE_NULL);
        Client existingClient = read(client.getEmail()); // Verifica se esiste già
        storage.remove(existingClient); // Rimuovi il vecchio record
        storage.add(client); // Aggiungi il record aggiornato
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Chiave non valida per l'eliminazione del Client");
        }

        String email = (String) keys[0];
        Client existingClient = read(email);
        storage.remove(existingClient);
    }

    private boolean isEmailUnique(String email) {
        return storage.stream().noneMatch(client -> client.getEmail().equals(email));
    }

    private boolean isPhoneNumberUnique(String phoneNumber) {
        return storage.stream().noneMatch(client -> client.getPhoneNumber().equals(phoneNumber));
    }

    @Override
    public List<Client> readAll() {
        return new ArrayList<>(storage);
    }
}
