package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.BeanClientEReservationDetails;
import org.example.bean.BeanClientDetails;
import org.example.bean.BeanReservationDetails;
import org.example.view.CustomerView;

import java.util.List;

public class CustomerViewController {
    private CustomerView customerView;
    private BookRoom bookRoom;

    public CustomerViewController() {
        this.customerView = new CustomerView();
        this.bookRoom = new BookRoom();
        loadClientAndReservations();
    }

    private void addEventHandlers(){

    }

    private void loadClientAndReservations() {
        BeanClientEReservationDetails details = bookRoom.getClientAndReceptionistDetails();
        if (details == null) {
            System.out.println("Errore nel caricamento dei dati del cliente.");
            return;
        }

        populateClientDetails(details.getClientDetails());
        populateReservations(details.getReservationDetails());
    }

    private void populateClientDetails(BeanClientDetails clientDetails) {
        customerView.getCustomerInfoBox().getChildren().clear();
        if (clientDetails != null) {
            customerView.getCustomerInfoBox().getChildren().addAll(
                    new javafx.scene.control.Label("Nome: " + clientDetails.getFirstName()),
                    new javafx.scene.control.Label("Cognome: " + clientDetails.getLastName()),
                    new javafx.scene.control.Label("Email: " + clientDetails.getEmail()),
                    new javafx.scene.control.Label("Telefono: " + clientDetails.getPhoneNumber()),
                    new javafx.scene.control.Label("Data di Nascita: " + clientDetails.getBirthDate()),
                    new javafx.scene.control.Label("Codice Fiscale: " + clientDetails.getTaxCode())
            );
        }
    }

    private void populateReservations(List<BeanReservationDetails> reservationDetails) {
        customerView.getReservationsBox().getChildren().clear();
        for (BeanReservationDetails reservation : reservationDetails) {
            if(reservation.getRoomNumber()!=0){
                customerView.getReservationsBox().getChildren().add(
                        new javafx.scene.control.Label(
                                "ID: " + reservation.getReservationId() +
                                        " | Stanza: " + reservation.getRoomNumber() +
                                        " | Prezzo: " + reservation.getPrice() +
                                        " | Stato: " + reservation.getStatus() +
                                        " | Check-in: " + reservation.getTimetable().getStartDate() +
                                        " | Check-out: " + reservation.getTimetable().getEndDate()
                        )
                );

            }
        }
    }

    public VBox getRoot() {
        return customerView.getRoot();
    }
}