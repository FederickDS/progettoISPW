package org.example.dao;

import org.example.entity.Client;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClientDaoMemory implements GenericDao<Client> {
    private final Map<String, Client> storage = new HashMap<>();
    private final String CLIENT_NOT_FOUND = "Client not found";
    private final String CLIENT_CANNOT_BE_NULL = "Client cannot be null";

    @Override
    public void create(Client client) throws SQLException {
        if (client == null) throw new SQLException(CLIENT_CANNOT_BE_NULL);
        if (storage.containsKey(client.getEmail()))
            throw new SQLException("Client with this email already exists");
        storage.put(client.getEmail(), client);
    }

    @Override
    public Client read(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException(CLIENT_NOT_FOUND);
        return storage.get(email);
    }

    @Override
    public void update(Client client) throws SQLException {
        if (client == null) throw new SQLException(CLIENT_CANNOT_BE_NULL);
        if (!storage.containsKey(client.getEmail()))
            throw new SQLException(CLIENT_NOT_FOUND);
        storage.put(client.getEmail(), client);
    }

    @Override
    public void delete(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException(CLIENT_NOT_FOUND);
        storage.remove(email);
    }
}
