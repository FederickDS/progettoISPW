package org.example.entity;

// Classe che rappresenta un servizio
class Service extends BaseModel {
    public Service() {
    }

    public Service(String description, int maxParticipants) {
        super(description, maxParticipants);
    }
}