package org.example.bean;

import java.io.Serializable;

public class PaymentBean implements Serializable {
    private int roomNumber;
    private String price;
    private String paymentDeadline;
    private int reservationID;

    public PaymentBean() {}

    public PaymentBean(int roomNumber, String price, String paymentDeadline, int reservationID) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.paymentDeadline = paymentDeadline;
        this.reservationID = reservationID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public int getReservationID() {
        return reservationID;
    }
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
}
