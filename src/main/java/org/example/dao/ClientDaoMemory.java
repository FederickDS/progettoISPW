package org.example.dao;

import org.example.entity.Client;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClientDaoMemory implements GenericDao<Client> {
    private final Map<String, Client> storage = new HashMap<>();

    @Override
    public void create(Client client) throws SQLException {
        if (client == null) throw new SQLException("Client cannot be null");
        if (storage.containsKey(client.getEmail()))
            throw new SQLException("Client with this email already exists");
        storage.put(client.getEmail(), client);
    }

    @Override
    public Client read(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException("Client not found");
        return storage.get(email);
    }

    @Override
    public void update(Client client) throws SQLException {
        if (client == null) throw new SQLException("Client cannot be null");
        if (!storage.containsKey(client.getEmail()))
            throw new SQLException("Client not found");
        storage.put(client.getEmail(), client);
    }

    @Override
    public void delete(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException("Client not found");
        storage.remove(email);
    }
}
