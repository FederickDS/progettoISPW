package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.entity.DailyTimeInterval;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyTimeIntervalDaoFile implements GenericDao<DailyTimeInterval> {
    private static final String FILE_PATH = "daily_time_intervals.json";
    private final Gson gson;
    private List<DailyTimeInterval> intervals;

    public DailyTimeIntervalDaoFile() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registra l'adapter
                .setPrettyPrinting()
                .create();
        intervals = loadFromFile();
    }

    private List<DailyTimeInterval> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<DailyTimeInterval>>() {}.getType();
            List<DailyTimeInterval> loadedIntervals = gson.fromJson(reader, listType);
            return loadedIntervals != null ? loadedIntervals : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(intervals, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(DailyTimeInterval entity) {
        if (read(entity.getStartDate(), entity.getEndDate(), entity.getType()) != null) {
            throw new IllegalArgumentException("DailyTimeInterval already exists: " + entity);
        }
        intervals.add(entity);
        saveToFile();
    }

    @Override
    public DailyTimeInterval read(Object... keys) {
        if (keys.length != 3 || !(keys[0] instanceof LocalDate) || !(keys[1] instanceof LocalDate) || !(keys[2] instanceof String)) {
            throw new IllegalArgumentException("Invalid keys for reading DailyTimeInterval.");
        }
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        return intervals.stream()
                .filter(interval -> interval.getStartDate().equals(startDate) &&
                        interval.getEndDate().equals(endDate) &&
                        interval.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(DailyTimeInterval entity) {
        delete(entity.getStartDate(), entity.getEndDate(), entity.getType());
        create(entity);
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 3 || !(keys[0] instanceof LocalDate) || !(keys[1] instanceof LocalDate) || !(keys[2] instanceof String)) {
            throw new IllegalArgumentException("Invalid keys for deleting DailyTimeInterval.");
        }
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        intervals.removeIf(interval -> interval.getStartDate().equals(startDate) &&
                interval.getEndDate().equals(endDate) &&
                interval.getType().equals(type));
        saveToFile();
    }

    @Override
    public List<DailyTimeInterval> readAll() {
        return new ArrayList<>(intervals);
    }
}
