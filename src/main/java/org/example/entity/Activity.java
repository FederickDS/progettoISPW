package org.example.entity;

// Classe che rappresenta un'attività
public class Activity extends BaseModel {
    public Activity() {
    }

    public Activity(String name, String description, int maxParticipants) {
        super(name, description, maxParticipants);
    }
}