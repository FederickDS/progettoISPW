package org.example.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class TimeInterval implements Serializable {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type; // Nuovo campo per il tipo di intervallo

    public TimeInterval(LocalDate startDate, LocalDate endDate, String type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        return !checkIn.isBefore(startDate) && !checkOut.isAfter(endDate);
    }
}
