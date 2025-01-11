package org.example.dao;

import org.example.entity.Receptionist;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReceptionistDaoMemory implements GenericDao<Receptionist> {
    private final Map<String, Receptionist> storage = new HashMap<>();

    @Override
    public void create(Receptionist receptionist) throws SQLException {
        if (receptionist == null) throw new SQLException("Receptionist cannot be null");
        if (storage.containsKey(receptionist.getEmail()))
            throw new SQLException("Receptionist with this email already exists");
        storage.put(receptionist.getEmail(), receptionist);
    }

    @Override
    public Receptionist read(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException("Receptionist not found");
        return storage.get(email);
    }

    @Override
    public void update(Receptionist receptionist) throws SQLException {
        if (receptionist == null) throw new SQLException("Receptionist cannot be null");
        if (!storage.containsKey(receptionist.getEmail()))
            throw new SQLException("Receptionist not found");
        storage.put(receptionist.getEmail(), receptionist);
    }

    @Override
    public void delete(String email) throws SQLException {
        if (!storage.containsKey(email)) throw new SQLException("Receptionist not found");
        storage.remove(email);
    }
}
