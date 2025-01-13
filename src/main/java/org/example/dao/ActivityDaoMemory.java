package org.example.dao;

import org.example.entity.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityDaoMemory implements GenericDao<Activity> {
    private final List<Activity> activities = new ArrayList<>();

    public ActivityDaoMemory() {
        activities.add(new Activity("Escursione guidata", "Esplora la costa con una guida esperta", 12));
        activities.add(new Activity("Giro in barca al tramonto", "Ammira il tramonto dal mare", 8));
    }

    @Override
    public void create(Activity entity) {
        activities.add(entity);
    }

    @Override
    public Activity read(String name) {
        return activities.stream()
                .filter(activity -> activity.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Activity entity) {
        Activity existing = read(entity.getName());
        if (existing != null) {
            activities.remove(existing);
            activities.add(entity);
        }
    }

    @Override
    public void delete(String name) {
        activities.removeIf(activity -> activity.getName().equalsIgnoreCase(name));
    }

    // Nuovo metodo readAll
    public List<Activity> readAll() {
        return new ArrayList<>(activities); // Restituisce una copia della lista
    }
}
