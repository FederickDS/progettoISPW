package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class BookingRoom {
    protected VBox root;
    private final DatePicker checkInPicker;
    private final DatePicker checkOutPicker;
    private final TextField participantsField;
    private final Button confirmButton;
    private final Button cancelButton;

    // Aggiunti i messaggi di errore per i vari campi
    private final Label checkInErrorLabel;
    private final Label checkOutErrorLabel;
    private final Label participantsErrorLabel;

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
        checkInErrorLabel = new Label(); // Messaggio di errore per check-in
        checkInErrorLabel.getStyleClass().add("error-message"); // Inizialmente non occupa spazio
        checkInErrorLabel.setVisible(false); // Messaggio nascosto

        Label checkOutLabel = new Label("Data di check-out:");
        checkOutPicker = new DatePicker();
        checkOutPicker.setPromptText("Seleziona la data di check-out");
        checkOutErrorLabel = new Label(); // Messaggio di errore per check-out
        checkOutErrorLabel.getStyleClass().add("error-message");
        checkOutErrorLabel.setVisible(false); // Messaggio nascosto

        // Nuovo campo per numero di partecipanti
        Label participantsLabel = new Label("Inserisci il numero di partecipanti:");
        participantsField = new TextField();
        participantsField.setPromptText("Numero di partecipanti");
        participantsField.setMaxWidth(200);
        participantsErrorLabel = new Label(); // Messaggio di errore per il numero di partecipanti
        participantsErrorLabel.getStyleClass().add("error-message");
        participantsErrorLabel.setVisible(false); // Messaggio nascosto

        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");

        // Aggiunta degli elementi alla root, includendo anche i messaggi di errore
        root.getChildren().addAll(
                title,
                checkInLabel, checkInPicker, checkInErrorLabel,
                checkOutLabel, checkOutPicker, checkOutErrorLabel,
                participantsLabel, participantsField, participantsErrorLabel,
                confirmButton, cancelButton
        );
    }

    // Metodi di accesso
    public LocalDate getCheckInDate() {
        return checkInPicker.getValue();
    }

    public LocalDate getCheckOutDate() {
        return checkOutPicker.getValue();
    }

    public int getParticipants() {
        try {
            return Integer.parseInt(participantsField.getText());
        } catch (NumberFormatException e) {
            return 0; // Ritorna 0 se il valore inserito non è valido
        }
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
