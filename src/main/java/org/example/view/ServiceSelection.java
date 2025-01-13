package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ServiceSelection {
    protected VBox root;
    private final VBox serviceSection;
    private final VBox activitySection;
    private final Button confirmButton;
    private final Button cancelButton;

    public ServiceSelection() {
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Sezione per i servizi
        Label serviceLabel = new Label("Servizi Inclusi:");
        this.serviceSection = new VBox(10);
        this.serviceSection.setStyle("-fx-alignment: center-left;");
        this.serviceSection.getChildren().add(serviceLabel);

        // Sezione per le attività
        Label activityLabel = new Label("Attività Disponibili:");
        this.activitySection = new VBox(10);
        this.activitySection.setStyle("-fx-alignment: center-left;");
        this.activitySection.getChildren().add(activityLabel);

        // Bottoni di azione
        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");
        VBox buttonSection = new VBox(10, confirmButton, cancelButton);
        buttonSection.setStyle("-fx-alignment: center;");

        // Aggiungi sezioni al layout principale
        root.getChildren().addAll(serviceSection, activitySection, buttonSection);
    }

    // Getter per i bottoni
    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public VBox getRoot(){return root;}

    //getter per aggiunta attivita e servizi
    public VBox getServiceSection() {
        return serviceSection;
    }

    public VBox getActivitySection() {
        return activitySection;
    }
}
