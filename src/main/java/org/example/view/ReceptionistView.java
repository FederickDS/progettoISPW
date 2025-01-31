package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ReceptionistView {
    private VBox root;
    private VBox reservationsBox;
    private VBox reservationsContainer;
    private Button backButton;
    private static final String FX_STYLE = "-fx-font-size: 18px; -fx-font-weight: bold;";

    public ReceptionistView() {
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Sezione prenotazioni
        Label reservationsLabel = new Label("Prenotazioni in attesa di conferma");
        reservationsLabel.setStyle(FX_STYLE);

        reservationsBox = new VBox(5);

        // Aggiunta di una ScrollPane per le prenotazioni
        ScrollPane scrollPane = new ScrollPane(reservationsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(600);

        this.reservationsContainer = new VBox(10, scrollPane);
        reservationsContainer.setPadding(new Insets(10, 0, 10, 0));

        backButton = new Button("Indietro");

        // Struttura principale
        root.getChildren().addAll(reservationsLabel,reservationsContainer, backButton);
    }

    public VBox getRoot() {
        return root;
    }

    public VBox getReservationsBox() {
        return reservationsBox;
    }

    public Button getBackButton() {
        return backButton;
    }
}
