package org.example.bean;

import java.io.Serializable;
import java.util.List;

public class BeanClientEReservationDetails implements Serializable {
    private BeanClientDetails clientDetails;
    private List<BeanReservationDetails> reservationDetails;

    public BeanClientEReservationDetails() {
        //classe bean
    }

    public BeanClientEReservationDetails(BeanClientDetails clientDetails, List<BeanReservationDetails> reservationDetails) {
        this.clientDetails = clientDetails;
        this.reservationDetails = reservationDetails;
    }

    public BeanClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(BeanClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public List<BeanReservationDetails> getReservationDetails() {
        return reservationDetails;
    }

    public void setReservationDetails(List<BeanReservationDetails> reservationDetails) {
        this.reservationDetails = reservationDetails;
    }
}