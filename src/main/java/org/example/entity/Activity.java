package org.example.entity;

// Classe che rappresenta un'attività
class Activity extends BaseModel {
    public Activity() {
    }

    public Activity(String description, int maxParticipants) {
        super(description, maxParticipants);
    }
}