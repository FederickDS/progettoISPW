package org.example.graphic_controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.BeanReservationDetails;
import org.example.view.ReceptionistView;
import java.util.List;

public class ReceptionistViewController {
    private ReceptionistView receptionistView;
    private BookRoom bookRoom;
    private NavigationService navigationService;

    public ReceptionistViewController(NavigationService navigationService) {
        this.receptionistView = new ReceptionistView();
        this.bookRoom = new BookRoom();
        this.navigationService = navigationService;

        loadReservations();
        addEventHandlers();
    }

    private void addEventHandlers() {
        receptionistView.getBackButton().setOnAction(e -> navigateBack());
    }

    private void navigateBack() {
        navigationService.navigateToHomePage(navigationService);
    }

    private void loadReservations() {
        List<BeanReservationDetails> reservations = bookRoom.getAllReservations();
        receptionistView.getReservationsBox().getChildren().clear();

        for (BeanReservationDetails reservation : reservations) {
            VBox reservationBox = new VBox(5,
                    new Label("ID: " + reservation.getReservationId()),
                    new Label("Stanza: " + reservation.getRoomNumber()),
                    new Label("Prezzo: " + reservation.getPrice()),
                    new Label("Stato: " + reservation.getStatus()),
                    new Label("Check-in: " + reservation.getTimetable().getStartDate()),
                    new Label("Check-out: " + reservation.getTimetable().getEndDate())
            );

            Button confirmButton = new Button("Conferma Prenotazione");
            confirmButton.setOnAction(e -> confirmReservation(reservation.getReservationId()));
            reservationBox.getChildren().add(confirmButton);

            receptionistView.getReservationsBox().getChildren().add(reservationBox);
        }
    }

    private void confirmReservation(int reservationId) {
        boolean success = bookRoom.confirmReservation(reservationId);
        if (success) {
            System.out.println("Prenotazione " + reservationId + " confermata con successo.");
            loadReservations(); // Ricarica la lista dopo la conferma
        } else {
            System.out.println("Errore nella conferma della prenotazione " + reservationId);
        }
    }

    public VBox getRoot() {
        return receptionistView.getRoot();
    }
}
