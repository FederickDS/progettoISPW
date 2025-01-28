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

    public void printReservationDetails() {
        System.out.println("=== Dettagli Prenotazione ===");
        System.out.println("ID Prenotazione: " + reservationId);

        if (timetable != null) {
            System.out.println("Intervallo di Tempo: " + timetable.toString());
        } else {
            System.out.println("Intervallo di Tempo: Non assegnato");
        }

        if (room != null) {
            System.out.println("Stanza: " + room.getRoomNumber());
        } else {
            System.out.println("Stanza: Non assegnata");
        }

        System.out.println("Clienti:");
        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                System.out.println("  - " + client.getEmail());
            }
        } else {
            System.out.println("  Nessun cliente registrato");
        }

        System.out.println("Servizi Inclusi:");
        if (freeServices != null && !freeServices.isEmpty()) {
            for (Service service : freeServices) {
                System.out.println("  - " + service.getName());
            }
        } else {
            System.out.println("  Nessun servizio selezionato");
        }

        System.out.println("Attività Incluse:");
        if (freeActivities != null && !freeActivities.isEmpty()) {
            for (Activity activity : freeActivities) {
                System.out.println("  - " + activity.getName());
            }
        } else {
            System.out.println("  Nessuna attività selezionata");
        }
    }
}