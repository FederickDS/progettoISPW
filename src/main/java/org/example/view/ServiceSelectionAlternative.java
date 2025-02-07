package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ServiceSelectionAlternative {
    protected VBox root;
    private final VBox serviceSection;
    private final VBox activitySection;
    private final ComboBox<String> activityDropdown;
    private final Button addActivityButton;
    private final Button confirmButton;
    private final Button cancelButton;
    private static final String FX_ALIGNMENT = "-fx-alignment: center-left;";

    public ServiceSelectionAlternative() {
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Sezione per i servizi
        Label serviceLabel = new Label("Servizi Inclusi:");
        this.serviceSection = new VBox(10);
        this.serviceSection.setStyle(FX_ALIGNMENT);
        this.serviceSection.getChildren().add(serviceLabel);

        // Sezione per le attività
        Label activityLabel = new Label("Attività Disponibili:");
        this.activitySection = new VBox(10);
        this.activitySection.setStyle(FX_ALIGNMENT);

        // ComboBox per la selezione dell'attività
        activityDropdown = new ComboBox<>();
        addActivityButton = new Button("Aggiungi Attività");

        VBox activityControls = new VBox(10, activityDropdown, addActivityButton);
        activityControls.setStyle(FX_ALIGNMENT);

        this.activitySection.getChildren().addAll(activityLabel, activityControls);

        // Bottoni di azione
        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");
        VBox buttonSection = new VBox(10, confirmButton, cancelButton);
        buttonSection.setStyle("-fx-alignment: center;");

        // Aggiungi sezioni al layout principale
        root.getChildren().addAll(serviceSection, activitySection, buttonSection);
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getAddActivityButton() {
        return addActivityButton;
    }

    public ComboBox<String> getActivityDropdown() {
        return activityDropdown;
    }

    public VBox getRoot() {
        return root;
    }

    public VBox getServiceSection() {
        return serviceSection;
    }

    public VBox getActivitySection() {
        return activitySection;
    }
}
