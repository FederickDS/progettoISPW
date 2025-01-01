package org.example.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Classe che rappresenta una prenotazione
class Reservation implements Serializable {
    private TimeInterval timetable;
    private List<Service> freeServices;
    private List<Activity> freeActivities;
    private Room room;
    private List<Client> clients;

    public Reservation() {
        this.freeServices = new ArrayList<>();
        this.freeActivities = new ArrayList<>();
        this.clients = new ArrayList<>();
    }

    public Reservation(TimeInterval timetable, List<Service> freeServices, List<Activity> freeActivities, Room room, List<Client> clients) {
        this.timetable = timetable;
        this.freeServices = freeServices;
        this.freeActivities = freeActivities;
        this.room = room;
        this.clients = clients;
    }

    public TimeInterval getTimetable() {
        return timetable;
    }

    public void setTimetable(TimeInterval timetable) {
        this.timetable = timetable;
    }

    public List<Service> getFreeServices() {
        return freeServices;
    }

    public void setFreeServices(List<Service> freeServices) {
        this.freeServices = freeServices;
    }

    public List<Activity> getFreeActivities() {
        return freeActivities;
    }

    public void setFreeActivities(List<Activity> freeActivities) {
        this.freeActivities = freeActivities;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}