package org.example.entity;

// Classe che rappresenta un servizio
public class Service extends BaseModel {
    public Service() {
    }

    public Service(String name, String description, int maxParticipants) {
        super(name, description, maxParticipants);
    }
}