package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Room;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoFile implements GenericDao<Room> {
    private static final String FILE_PATH = "rooms.json";
    private final Gson gson;
    private List<Room> rooms;

    public RoomDaoFile() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registra l'adapter
                .setPrettyPrinting()
                .create();
        rooms = loadFromFile();
    }

    private List<Room> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Room>>() {}.getType();
            List<Room> loadedRooms = gson.fromJson(reader, listType);
            return loadedRooms != null ? loadedRooms : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(rooms, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Room room) {
        if (read(room.getRoomNumber()) != null) {
            throw new IllegalArgumentException("Room already exists: " + room.getRoomNumber());
        }
        rooms.add(room);
        saveToFile();
    }

    @Override
    public Room read(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Invalid keys for reading Room.");
        }
        int roomNumber = (Integer) keys[0];

        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber() == room.getRoomNumber()) {
                rooms.set(i, room);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Room not found: " + room.getRoomNumber());
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof Integer)) {
            throw new IllegalArgumentException("Invalid keys for deleting Room.");
        }
        int roomNumber = (Integer) keys[0];

        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
        saveToFile();
    }

    @Override
    public List<Room> readAll() {
        return new ArrayList<>(rooms);
    }
}
