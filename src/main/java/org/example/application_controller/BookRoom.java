package org.example.application_controller;

import org.example.dao.DaoFactory;
import org.example.entity.Activity;
import org.example.entity.Client;
import org.example.entity.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRoom {
    private List<Client> clientList = new ArrayList<>();

    public BookRoom() {
        //il costruttore non deve definire attributi
    }

    public void setServicesToReservation() {
        //ora metodo dummy
    }

    public boolean checkHoursAndSave(LocalDate checkIn, LocalDate checkOut) {
        // se almeno una stanza tra quelle presenti nell'albergo e'disponibile, ritorna true
        return true;
    }

    public Client getFirstClient() {
        if (clientList.isEmpty()) {
            return null;
        }
        return clientList.get(0);
    }

    public void addFirstClient(Client client) {
        clientList.add(client);
    }

    public void addClient(Client client) {
        //AGGIUNGI LOGICA PER EVITARE INSERIMENTO INAPPROPRIATO O MULTIPLO
        clientList.add(client);
    }

    public List<Activity> getAvailableActivities() {
        return DaoFactory.getAvailableActivities();
    }

    public List<Service> getAvailableServices() {
        return DaoFactory.getAvailableServices();
    }
}
