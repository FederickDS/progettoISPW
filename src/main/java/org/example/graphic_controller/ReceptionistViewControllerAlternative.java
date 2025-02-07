package org.example.graphic_controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.BeanReservationDetails;
import org.example.view.ReceptionistViewAlternative;
import java.util.List;

public class ReceptionistViewControllerAlternative {
    private ReceptionistViewAlternative receptionistView;
    private BookRoom bookRoom;
    private NavigationService navigationService;

    public ReceptionistViewControllerAlternative(NavigationService navigationService) {
        this.receptionistView = new ReceptionistViewAlternative();
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
            VBox reservationCard = new VBox(10);
            reservationCard.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #f0f0f0;");

            reservationCard.getChildren().addAll(
                    new Label("ID: " + reservation.getReservationId()),
                    new Label("Stanza: " + reservation.getRoomNumber()),
                    new Label("Prezzo: " + reservation.getPrice()),
                    new Label("Stato: " + reservation.getStatus()),
                    new Label("Check-in: " + reservation.getTimetable().getStartDate()),
                    new Label("Check-out: " + reservation.getTimetable().getEndDate())
            );

            Button confirmButton = new Button("Conferma Prenotazione");
            confirmButton.setOnAction(e -> confirmReservation(reservation.getReservationId()));
            reservationCard.getChildren().add(confirmButton);

            receptionistView.getReservationsBox().getChildren().add(reservationCard);
        }
    }

    private void confirmReservation(int reservationId) {
        boolean success = bookRoom.confirmReservation(reservationId);
        if (success) {
            loadReservations(); // Ricarica la lista dopo la conferma
        }
    }

    public VBox getRoot() {
        return receptionistView.getRoot();
    }
}
