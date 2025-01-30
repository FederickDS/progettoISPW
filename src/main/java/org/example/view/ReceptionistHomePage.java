package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ReceptionistHomePage {
    private final VBox root;
    private final Button manageBookingsButton;
    private final Button manageActivitiesButton;

    public ReceptionistHomePage() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        manageBookingsButton = new Button("Gestione Prenotazioni");
        manageActivitiesButton = new Button("Gestione Attività");

        root.getChildren().addAll(manageBookingsButton, manageActivitiesButton);
    }

    public VBox getRoot() {
        return root;
    }

    public Button getManageBookingsButton() {
        return manageBookingsButton;
    }

    public Button getManageActivitiesButton() {
        return manageActivitiesButton;
    }
}