package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.entity.Activity;
import org.example.entity.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoMemory implements GenericDao<Client> {
    private final ObservableList<Client> storage;
    private static final String CLIENT_NOT_FOUND = "Client not found";
    private static final String CLIENT_CANNOT_BE_NULL = "Client cannot be null";

    public ClientDaoMemory() {
        storage = FXCollections.observableArrayList();
    }

    @Override
    public void create(Client client) throws SQLException {
        if (client == null) throw new SQLException(CLIENT_CANNOT_BE_NULL);
        if (!isEmailUnique(client.getEmail())) {
            throw new SQLException("Client with this email already exists");
        }
        storage.add(client);
    }

    @Override
    public Client read(String email) throws SQLException {
        return storage.stream()
                .filter(client -> client.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new SQLException(CLIENT_NOT_FOUND));
    }

    @Override
    public void update(Client client) throws SQLException {
        if (client == null) throw new SQLException(CLIENT_CANNOT_BE_NULL);
        Client existingClient = read(client.getEmail()); // Verifica se esiste già
        storage.remove(existingClient); // Rimuovi il vecchio record
        storage.add(client); // Aggiungi il record aggiornato
    }

    @Override
    public void delete(String email) throws SQLException {
        Client existingClient = read(email); // Verifica se esiste già
        storage.remove(existingClient);
    }

    private boolean isEmailUnique(String email) {
        return storage.stream().noneMatch(client -> client.getEmail().equals(email));
    }

    // Nuovo metodo readAll
    public List<Client> readAll() {
        return new ArrayList<>(storage); // Restituisce una copia della lista
    }
}
