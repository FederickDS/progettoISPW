package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RoomBookingOptionsViewAlternative {
    private final VBox root;
    private final ToggleGroup toggleGroup;
    private final RadioButton loginOption;
    private final RadioButton registerOption;
    private final RadioButton essentialInfoOption;
    private final Text selectionError;
    private final Button confirmButton;
    private final Button backButton;

    public RoomBookingOptionsViewAlternative() {
        // Layout della pagina
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Titolo e descrizione
        Label title = new Label("Seleziona un'opzione per prenotare la stanza");
        Label description = new Label("Scegli se accedere, registrarti o inserire solo le informazioni essenziali.");

        // Radio buttons per le opzioni
        toggleGroup = new ToggleGroup();
        loginOption = new RadioButton("Accedi");
        registerOption = new RadioButton("Registrati");
        essentialInfoOption = new RadioButton("Prenota senza registrarti");

        loginOption.setToggleGroup(toggleGroup);
        registerOption.setToggleGroup(toggleGroup);
        essentialInfoOption.setToggleGroup(toggleGroup);

        selectionError = new Text("Seleziona un'opzione per prenotare la stanza");
        selectionError.setVisible(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma");
        backButton = new Button("Indietro");

        // Aggiungi elementi alla root
        root.getChildren().addAll(title, description, loginOption, registerOption, essentialInfoOption, selectionError, confirmButton, backButton);
    }

    public VBox getRoot() {
        return root;
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void showSelectionError() {
        selectionError.setVisible(true);
    }
}
