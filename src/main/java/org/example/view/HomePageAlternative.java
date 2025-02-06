package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class HomePageAlternative {
    protected VBox root;
    private final RadioButton bookRoomOption;
    private final RadioButton bookActivityOption;
    private final RadioButton loginOption;
    private final Button confirmButton;
    private final ToggleGroup selectionGroup;
    private final Label selectionError;

    public HomePageAlternative() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.setAlignment(Pos.CENTER);

        // Titolo e descrizione
        Label title = new Label("Benvenuti al nostro Hotel di Mare!");
        Label description = new Label("Effettuate la vostra prenotazione e godetevi il soggiorno.");

        // Gruppo di selezione con RadioButton
        selectionGroup = new ToggleGroup();

        bookRoomOption = new RadioButton("Prenota Stanza");
        bookActivityOption = new RadioButton("Prenota Attività");
        loginOption = new RadioButton("Accedi");

        bookRoomOption.setToggleGroup(selectionGroup);
        bookActivityOption.setToggleGroup(selectionGroup);
        loginOption.setToggleGroup(selectionGroup);

        // Messaggio di errore nascosto inizialmente
        selectionError = new Label("Seleziona un'opzione prima di confermare.");
        selectionError.setStyle("-fx-text-fill: red;");
        selectionError.setVisible(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma Scelta");

        // Aggiunta degli elementi alla root
        root.getChildren().addAll(
                title,
                description,
                bookRoomOption,
                bookActivityOption,
                loginOption,
                selectionError,
                confirmButton
        );
    }

    public RadioButton getBookRoomOption() {
        return bookRoomOption;
    }

    public RadioButton getBookActivityOption() {
        return bookActivityOption;
    }

    public RadioButton getLoginOption() {
        return loginOption;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public ToggleGroup getSelectionGroup() {
        return selectionGroup;
    }

    public void showSelectionError() {
        selectionError.setVisible(true);
    }

    public VBox getRoot() {
        return root;
    }
}
