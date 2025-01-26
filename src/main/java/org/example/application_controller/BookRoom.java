package org.example.application_controller;

import org.example.dao.DaoFactory;
import org.example.entity.Activity;
import org.example.entity.Client;
import org.example.entity.Service;
import org.example.entity.TimeInterval;
import org.example.exception.DatabaseConfigurationException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookRoom {
    private List<Client> clientList = new ArrayList<>();
    private List<Activity> selectedActivities = new ArrayList<>();
    private List<Service> selectedServices = new ArrayList<>();

    public BookRoom() {
        //il costruttore non deve definire attributi
    }

    public void setServicesToReservation(List<Activity> activities, List<Service> services) {
        this.selectedActivities = activities;
        this.selectedServices = services;
    }

    public boolean checkHoursAndSave(LocalDate checkIn, LocalDate checkOut) {
        // Ottieni gli intervalli di tempo relativi ai "giorni di apertura"
        List<TimeInterval> availableIntervals = DaoFactory.getTimeIntervalDao().readAll().stream()
                .filter(interval -> "giorni di apertura".equals(interval.getType()))  // Filtro per tipo "giorni di apertura"
                .toList();  // Usa toList() per raccogliere i risultati in una lista immutabile
        // Controlla se l'intervallo scelto è valido
        for (TimeInterval interval : availableIntervals) {
            if (interval.isAvailable(checkIn, checkOut)) {
                return true; // L'intervallo è valido
            }
        }
        return false; // Intervallo non valido
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

    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }
}
