package org.example.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class DailyTimeInterval implements Serializable {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type; // Nuovo campo per il tipo di intervallo

    public DailyTimeInterval(LocalDate startDate, LocalDate endDate, String type) {
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
        boolean checkInAfterOrEqual = checkIn.isAfter(startDate) || checkIn.isEqual(startDate);
        boolean checkOutBeforeOrEqual = checkOut.isBefore(endDate) || checkOut.isEqual(endDate);
        return (checkInAfterOrEqual && checkOutBeforeOrEqual);//intervalli separati
    }

    public boolean overlapsWith(LocalDate start, LocalDate end) {
        return !(end.isBefore(this.startDate) || start.isAfter(this.endDate));
    }

}
