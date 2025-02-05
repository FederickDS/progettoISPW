package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoFile implements GenericDao<Service> {
    private static final String FILE_PATH = "services.json";
    private final Gson gson;
    private List<Service> services;

    public ServiceDaoFile() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registra l'adapter
                .setPrettyPrinting()
                .create();
        services = loadFromFile();
    }

    private List<Service> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Service>>() {}.getType();
            List<Service> loadedServices = gson.fromJson(reader, listType);
            return loadedServices != null ? loadedServices : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(services, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Service entity) {
        if (read(entity.getName()) != null) {
            throw new IllegalArgumentException("Service already exists: " + entity.getName());
        }
        services.add(entity);
        saveToFile();
    }

    @Override
    public Service read(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid keys for reading Service.");
        }
        String name = (String) keys[0];

        return services.stream()
                .filter(service -> service.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Service entity) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getName().equals(entity.getName())) {
                services.set(i, entity);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Service not found: " + entity.getName());
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid keys for deleting Service.");
        }
        String name = (String) keys[0];

        services.removeIf(service -> service.getName().equals(name));
        saveToFile();
    }

    @Override
    public List<Service> readAll() {
        return new ArrayList<>(services);
    }
}
