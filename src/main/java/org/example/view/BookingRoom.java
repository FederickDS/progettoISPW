package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.StartupSettingsEntity;
import org.example.view.control.NavigationManager;
import org.example.view.control.NavigationService;

import java.time.LocalDate;
import java.util.logging.Logger;

public class BookingRoom {
    protected VBox root;
    private final DatePicker checkInPicker;
    private final DatePicker checkOutPicker;
    private final Button confirmButton;
    private final Button cancelButton;
    private Logger logger;

    public BookingRoom() {
        // Layout di base
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Elementi della view
        Label title = new Label("Prenota stanza");
        Label checkInLabel = new Label("Data di check-in:");
        checkInPicker = new DatePicker();
        checkInPicker.setPromptText("Seleziona la data di check-in");

        Label checkOutLabel = new Label("Data di check-out:");
        checkOutPicker = new DatePicker();
        checkOutPicker.setPromptText("Seleziona la data di check-out");

        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");

        // Aggiungi gli elementi alla root
        root.getChildren().addAll(title, checkInLabel, checkInPicker, checkOutLabel, checkOutPicker, confirmButton, cancelButton);
    }

    // Metodi di accesso
    public LocalDate getCheckInDate() {
        return checkInPicker.getValue();
    }

    public LocalDate getCheckOutDate() {
        return checkOutPicker.getValue();
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    // Metodo per mostrare la pagina
    public void display(Stage stage, NavigationService navigationService) {
        navigationService.display(stage, root,"Seleziona date e persone");
    }
}
