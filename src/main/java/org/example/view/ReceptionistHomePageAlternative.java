package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ReceptionistHomePageAlternative {
    private final VBox root;
    private final RadioButton manageBookingsOption;
    private final RadioButton manageActivitiesOption;
    private final RadioButton logoutOption;
    private final Button confirmButton;
    private final ToggleGroup selectionGroup;
    private final Label selectionError;

    public ReceptionistHomePageAlternative() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.setAlignment(Pos.CENTER);

        // Gruppo di selezione con RadioButton
        selectionGroup = new ToggleGroup();

        manageBookingsOption = new RadioButton("Gestione Prenotazioni");
        manageActivitiesOption = new RadioButton("Gestione Attività");
        logoutOption = new RadioButton("Logout");

        manageBookingsOption.setToggleGroup(selectionGroup);
        manageActivitiesOption.setToggleGroup(selectionGroup);
        logoutOption.setToggleGroup(selectionGroup);

        // Messaggio di errore nascosto inizialmente
        selectionError = new Label("Seleziona un'opzione prima di confermare.");
        selectionError.setStyle("-fx-text-fill: red;");
        selectionError.setVisible(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma Scelta");

        // Aggiunta degli elementi alla root
        root.getChildren().addAll(
                manageBookingsOption,
                manageActivitiesOption,
                logoutOption,
                selectionError,
                confirmButton
        );
    }

    public VBox getRoot() {
        return root;
    }

    public RadioButton getManageBookingsOption() {
        return manageBookingsOption;
    }

    public RadioButton getManageActivitiesOption() {
        return manageActivitiesOption;
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
