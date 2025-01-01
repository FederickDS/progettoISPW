package org.example.entity;

import java.io.Serializable;

// Classe base per rappresentare un modello con descrizione e numero massimo di partecipanti
abstract class BaseModel implements Serializable {
    private String name;
    private String description;
    private int maxParticipants;

    protected BaseModel() {
    }

    protected BaseModel(String description, int maxParticipants) {
        this.description = description;
        this.maxParticipants = maxParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}