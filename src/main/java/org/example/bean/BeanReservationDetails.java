package org.example.bean;

import org.example.entity.DailyTimeInterval;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BeanReservationDetails implements Serializable {
    private int reservationId;
    private DailyTimeInterval timeTable;
    private List<String> freeServices;
    private List<String> freeActivities;
    private int roomNumber;
    private List<String> clientEmails;
    private BigDecimal price;
    private String status;

    public BeanReservationDetails() {
        //classe bean
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public DailyTimeInterval getTimetable() {
        return timeTable;
    }

    public void setTimetable(DailyTimeInterval timeTable) {
        this.timeTable = timeTable;
    }

    public List<String> getFreeServices() {
        return freeServices;
    }

    public void setFreeServices(List<String> freeServices) {
        this.freeServices = freeServices;
    }

    public List<String> getFreeActivities() {
        return freeActivities;
    }

    public void setFreeActivities(List<String> freeActivities) {
        this.freeActivities = freeActivities;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<String> getClientEmails() {
        return clientEmails;
    }

    public void setClientEmails(List<String> clientEmails) {
        this.clientEmails = clientEmails;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
