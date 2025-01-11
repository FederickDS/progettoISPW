package org.example.dao;

import org.example.entity.Client;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClientDaoMemory implements GenericDao<Client> {
    private final Map<String, Client> storage = new HashMap<>();
    private static final String clientNotFound = "Client not found";
    private static final String clientCannotBeNull = "Client cannot be null";

    @Override
    public void create(Client client) throws SQLException {
        if (client == null) throw new SQLException(clientCannotBeNull);
        if (storage.containsKey(client.getEmail()))
            throw new SQLException("Client with this email already exists");
        storage.put(client.getEmail(), client);
    }

    @Override
    public Client read(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException(clientNotFound);
        return storage.get(email);
    }

    @Override
    public void update(Client client) throws SQLException {
        if (client == null) throw new SQLException(clientCannotBeNull);
        if (!storage.containsKey(client.getEmail()))
            throw new SQLException(clientNotFound);
        storage.put(client.getEmail(), client);
    }

    @Override
    public void delete(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException(clientNotFound);
        storage.remove(email);
    }
}
