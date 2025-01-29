package org.example.bean;

import java.io.Serializable;

public class PaymentBean implements Serializable {
    private int roomNumber;
    private String price;
    private String paymentDeadline;

    public PaymentBean() {}

    public PaymentBean(int roomNumber, String price, String paymentDeadline) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.paymentDeadline = paymentDeadline;
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
}
