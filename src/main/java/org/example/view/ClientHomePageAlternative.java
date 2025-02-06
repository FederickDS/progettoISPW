package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ClientHomePageAlternative {
    private final VBox root;
    private final RadioButton bookRoomOption;
    private final RadioButton bookActivityOption;
    private final RadioButton userInfoOption;
    private final RadioButton logoutOption;
    private final Button confirmButton;
    private final ToggleGroup selectionGroup;
    private final Label selectionError;

    public ClientHomePageAlternative() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.setAlignment(Pos.CENTER);

        // Gruppo di selezione con RadioButton
        selectionGroup = new ToggleGroup();

        bookRoomOption = new RadioButton("Prenota Stanza");
        bookActivityOption = new RadioButton("Prenota Attività");
        userInfoOption = new RadioButton("Le Tue Info");
        logoutOption = new RadioButton("Logout");

        bookRoomOption.setToggleGroup(selectionGroup);
        bookActivityOption.setToggleGroup(selectionGroup);
        userInfoOption.setToggleGroup(selectionGroup);
        logoutOption.setToggleGroup(selectionGroup);

        // Messaggio di errore nascosto inizialmente
        selectionError = new Label("Seleziona un'opzione prima di confermare.");
        selectionError.setStyle("-fx-text-fill: red;");
        selectionError.setVisible(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma Scelta");

        // Aggiungi alla root
        root.getChildren().addAll(
                bookRoomOption,
                bookActivityOption,
                userInfoOption,
                logoutOption,
                selectionError,
                confirmButton
        );
    }

    public VBox getRoot() {
        return root;
    }

    public RadioButton getBookRoomOption() {
        return bookRoomOption;
    }

    public RadioButton getBookActivityOption() {
        return bookActivityOption;
    }

    public RadioButton getUserInfoOption() {
        return userInfoOption;
    }

    public RadioButton getLogoutOption() {
        return logoutOption;
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
}
