package org.example.entity;

import java.io.Serializable;
import java.util.List;

// Classe che rappresenta una prenotazione
public class Reservation implements Serializable {
    private int reservationId; // Aggiunto per rappresentare l'ID della prenotazione
    private DailyTimeInterval timetable;
    private List<Service> freeServices;
    private List<Activity> freeActivities;
    private Room room;
    private List<Client> clients;

    public Reservation() {/*classe bean*/}

    // Getter e setter per reservationId
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public DailyTimeInterval getTimetable() {
        return timetable;
    }

    public void setTimetable(DailyTimeInterval timetable) {
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