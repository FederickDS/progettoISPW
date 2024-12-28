package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class ServiceSelection {
    protected VBox root;
    private Button confirmButton;
    private Button cancelButton;

    public ServiceSelection() {
        root = new VBox(20);
        root.setPrefSize(600, 500);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Sezione per i servizi
        Label serviceLabel = new Label("Servizi Inclusi:");
        CheckBox saunaCheckBox = new CheckBox("Sauna");
        CheckBox beachCheckBox = new CheckBox("Spiaggia");
        CheckBox tanningBedCheckBox = new CheckBox("Lettino Abbronzante");
        CheckBox bicycleCheckBox = new CheckBox("Bicicletta");

        VBox serviceSection = new VBox(10, serviceLabel, saunaCheckBox, beachCheckBox, tanningBedCheckBox, bicycleCheckBox);
        serviceSection.setStyle("-fx-alignment: center-left;");

        // Sezione per le attività
        Label activityLabel = new Label("Attività Disponibili:");
        CheckBox excursionCheckBox = new CheckBox("Escursione");
        CheckBox boatTripCheckBox = new CheckBox("Giro in Barca");
        CheckBox windsurfLessonCheckBox = new CheckBox("Lezione di Windsurf");
        CheckBox padelLessonCheckBox = new CheckBox("Lezione di Padel");
        CheckBox yogaLessonCheckBox = new CheckBox("Lezione di Yoga");

        VBox activitySection = new VBox(10, activityLabel, excursionCheckBox, boatTripCheckBox, windsurfLessonCheckBox, padelLessonCheckBox, yogaLessonCheckBox);
        activitySection.setStyle("-fx-alignment: center-left;");

        // Bottoni di azione
        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");
        VBox buttonSection = new VBox(10, confirmButton, cancelButton);
        buttonSection.setStyle("-fx-alignment: center;");

        // Aggiungi sezioni al layout principale
        root.getChildren().addAll(serviceSection, activitySection, buttonSection);

        // Imposta gli stili specifici
        setStyle(serviceLabel, activityLabel, confirmButton, cancelButton);
    }

    // Metodo astratto per impostare lo stile specifico
    protected abstract void setStyle(Label serviceLabel, Label activityLabel, Button confirmButton, Button cancelButton);

    protected void setStyleDuplicate(String buttonStyle, String buttonHoverStyle, Button confirmButton, Button cancelButton) {
        confirmButton.setStyle(buttonStyle);
        confirmButton.setOnMouseEntered(e -> confirmButton.setStyle(buttonHoverStyle));
        confirmButton.setOnMouseExited(e -> confirmButton.setStyle(buttonStyle));

        cancelButton.setStyle(buttonStyle);
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(buttonHoverStyle));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(buttonStyle));
    }

    // Getter per i bottoni
    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    // Metodo per mostrare la pagina
    public void display(Stage stage) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Selezione Servizi e Attività");
        stage.show();
    }
}
