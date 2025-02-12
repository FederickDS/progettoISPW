package org.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

// Classe che rappresenta una prenotazione
public class Reservation implements Serializable {
    private int reservationId; // Aggiunto per rappresentare l'ID della prenotazione
    private DailyTimeInterval timetable;
    private List<Service> freeServices;
    private List<Activity> freeActivities;
    private Room room;
    private List<Client> clients;
    private BigDecimal price; // Aggiunto attributo prezzo
    private ReservationStatus status; // Nuovo attributo

    public Reservation() {
        this.status = ReservationStatus.PENDING;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public boolean isPaid() {
        return status == ReservationStatus.BOOKED;
    }

    public void confirmPayment() {
        if (status == ReservationStatus.PENDING) {
            this.status = ReservationStatus.BOOKED;
        }
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Metodo per calcolare il prezzo della prenotazione
    public void calculatePrice() {
        if (timetable == null || room == null) {
            throw new IllegalStateException("Timetable e Room devono essere impostati prima di calcolare il prezzo.");
        }

        long numberOfDays = timetable.getNumberOfDays();

        // Prezzo base per giorno basato su capacità e piano della stanza
        BigDecimal basePricePerDay = BigDecimal.valueOf(room.getMaxPeople() * 50L + room.getFloor() * 10L);

        // Prezzo aggiuntivo per servizi e attività gratuiti
        BigDecimal addedServicePrice = BigDecimal.ZERO;

        if (freeServices != null) {
            addedServicePrice = addedServicePrice.add(BigDecimal.valueOf(freeServices.size() * 5L));
        }
        if (freeActivities != null) {
            addedServicePrice = addedServicePrice.add(BigDecimal.valueOf(freeActivities.size() * 10L));
        }

        // Calcolo del prezzo totale
        BigDecimal totalPrice = (basePricePerDay.add(addedServicePrice)).multiply(BigDecimal.valueOf(numberOfDays));

        // Assicuriamoci che il prezzo non sia negativo
        if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            totalPrice = BigDecimal.ZERO;
        }

        // Arrotondamento a due cifre decimali
        this.price = totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public String getPaymentDeadline() {
        LocalDate today = LocalDate.now();
        long daysRemaining = ChronoUnit.DAYS.between(today, getTimetable().getStartDate());

        long deadline = Math.min(daysRemaining, 7); // Mostra massimo 7 giorni
        return deadline > 0 ? deadline + " giorni rimanenti" : "Scadenza superata";
    }

}