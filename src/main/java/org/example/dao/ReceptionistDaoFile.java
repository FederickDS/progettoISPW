package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Receptionist;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDaoFile implements GenericDao<Receptionist> {
    private static final String FILE_PATH = "receptionists.json";
    private final Gson gson;
    private List<Receptionist> receptionists;

    public ReceptionistDaoFile() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registra l'adapter
                .setPrettyPrinting()
                .create();
        receptionists = loadFromFile();
    }

    private List<Receptionist> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Receptionist>>() {}.getType();
            List<Receptionist> loadedReceptionists = gson.fromJson(reader, listType);
            return loadedReceptionists != null ? loadedReceptionists : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(receptionists, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Receptionist receptionist) {
        if (read(receptionist.getEmail()) != null) {
            throw new IllegalArgumentException("Receptionist already exists: " + receptionist.getEmail());
        }
        receptionists.add(receptionist);
        saveToFile();
    }

    @Override
    public Receptionist read(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid keys for reading Receptionist.");
        }
        String email = (String) keys[0];

        return receptionists.stream()
                .filter(receptionist -> receptionist.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Receptionist receptionist) {
        for (int i = 0; i < receptionists.size(); i++) {
            if (receptionists.get(i).getEmail().equals(receptionist.getEmail())) {
                receptionists.set(i, receptionist);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Receptionist not found: " + receptionist.getEmail());
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid keys for deleting Receptionist.");
        }
        String email = (String) keys[0];

        receptionists.removeIf(receptionist -> receptionist.getEmail().equals(email));
        saveToFile();
    }

    @Override
    public List<Receptionist> readAll() {
        return new ArrayList<>(receptionists);
    }
}
