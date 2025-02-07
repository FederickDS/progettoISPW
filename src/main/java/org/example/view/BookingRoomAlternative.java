package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class BookingRoomAlternative {
    protected VBox root;
    private final TextField checkInField;
    private final TextField checkOutField;
    private final ComboBox<Integer> participantsDropdown;
    private final Button confirmButton;
    private final Button cancelButton;

    // Aggiunti i messaggi di errore per i vari campi
    private final Label checkInErrorLabel;
    private final Label checkOutErrorLabel;
    private final Label participantsErrorLabel;
    private static final String ERROR_MESSAGE = "error-message";

    public BookingRoomAlternative() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Prenota stanza");

        Label checkInLabel = new Label("Data di check-in:");
        checkInField = new TextField();
        checkInField.setPromptText("Inserisci la data di check-in (YYYY-MM-DD)");
        checkInErrorLabel = new Label();
        checkInErrorLabel.getStyleClass().add(ERROR_MESSAGE);
        checkInErrorLabel.setVisible(false);

        Label checkOutLabel = new Label("Data di check-out:");
        checkOutField = new TextField();
        checkOutField.setPromptText("Inserisci la data di check-out (YYYY-MM-DD)");
        checkOutErrorLabel = new Label();
        checkOutErrorLabel.getStyleClass().add(ERROR_MESSAGE);
        checkOutErrorLabel.setVisible(false);

        Label participantsLabel = new Label("Numero di partecipanti:");
        participantsDropdown = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            participantsDropdown.getItems().add(i);
        }
        participantsDropdown.setPromptText("Seleziona numero di partecipanti");
        participantsErrorLabel = new Label();
        participantsErrorLabel.getStyleClass().add(ERROR_MESSAGE);
        participantsErrorLabel.setVisible(false);

        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");

        root.getChildren().addAll(
                title,
                checkInLabel, checkInField, checkInErrorLabel,
                checkOutLabel, checkOutField, checkOutErrorLabel,
                participantsLabel, participantsDropdown, participantsErrorLabel,
                confirmButton, cancelButton
        );
    }

    public String getCheckInDate() {
        return checkInField.getText();
    }

    public String getCheckOutDate() {
        return checkOutField.getText();
    }

    public Integer getParticipants() {
        return participantsDropdown.getValue();
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public VBox getRoot() {
        return root;
    }

    public void hideAllErrors() {
        checkInErrorLabel.setVisible(false);
        checkOutErrorLabel.setVisible(false);
        participantsErrorLabel.setVisible(false);
    }

    // Metodi per gestire la visualizzazione degli errori
    public void setCheckInError(String message) {
        checkInErrorLabel.setText(message);
        checkInErrorLabel.setVisible(true); // Mostra il messaggio
    }

    public void setCheckOutError(String message) {
        checkOutErrorLabel.setText(message);
        checkOutErrorLabel.setVisible(true); // Mostra il messaggio
    }

    public void setParticipantsError(String message) {
        participantsErrorLabel.setText(message);
        participantsErrorLabel.setVisible(true); // Mostra il messaggio
    }
}
