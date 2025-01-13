package org.example.entity;

import java.io.Serializable;

// Classe base per rappresentare un modello con descrizione e numero massimo di partecipanti
public abstract class BaseModel implements Serializable {
    private String name;
    private String description;
    private int maxParticipants;

    protected BaseModel() {/*classe bean*/}

    protected BaseModel(String name, String description, int maxParticipants) {
        this.name = name;
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