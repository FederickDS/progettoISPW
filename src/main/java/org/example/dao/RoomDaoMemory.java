package org.example.dao;

import org.example.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomDaoMemory implements GenericDao<Room> {
    private final List<Room> rooms = new ArrayList<>();

    public RoomDaoMemory() {
        // Aggiungi alcune stanze di esempio
        rooms.add(new Room(101, 2, 1));
        rooms.add(new Room(102, 3, 1));
        rooms.add(new Room(201, 4, 2));
    }

    @Override
    public void create(Room room) {
        if (read(room.getRoomNumber()) != null) {
            System.out.println("La stanza con il numero " + room.getRoomNumber() + " è già presente.");
            return;
        }
        rooms.add(room);
    }

    @Override
    public Room read(Object... keys) {
        if (keys.length != 1) return null;
        int roomNumber = (int) keys[0];
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Room room) {
        Room existingRoom = read(room.getRoomNumber());
        if (existingRoom != null) {
            existingRoom.setMaxPeople(room.getMaxPeople());
            existingRoom.setFloor(room.getFloor());
        } else {
            System.out.println("La stanza con il numero " + room.getRoomNumber() + " non esiste.");
        }
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 1) return;
        int roomNumber = (int) keys[0];
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }

    @Override
    public List<Room> readAll() {
        return new ArrayList<>(rooms);
    }

}
