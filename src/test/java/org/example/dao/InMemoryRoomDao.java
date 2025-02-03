package org.example.dao;

import org.example.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRoomDao implements GenericDao<Room> {
    private final List<Room> rooms = new ArrayList<>();

    @Override
    public void create(Room room) throws SQLException {
        rooms.add(room);
    }

    @Override
    public Room read(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Il metodo read accetta un solo parametro di tipo Integer (roomNumber)");
        }
        int roomNumber = (Integer) keys[0];
        return rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Room room) throws SQLException {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber() == room.getRoomNumber()) {
                rooms.set(i, room);
                return;
            }
        }
        throw new SQLException("Room con numero " + room.getRoomNumber() + " non trovata.");
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Il metodo delete accetta un solo parametro di tipo Integer (roomNumber)");
        }
        int roomNumber = (Integer) keys[0];
        rooms.removeIf(r -> r.getRoomNumber() == roomNumber);
    }

    @Override
    public List<Room> readAll() {
        return new ArrayList<>(rooms); // Restituisce una copia per evitare modifiche dirette
    }
}
