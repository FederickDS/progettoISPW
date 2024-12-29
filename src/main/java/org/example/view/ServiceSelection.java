package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.StartupSettingsEntity;
import org.example.view.control.NavigationService;

import java.util.logging.Logger;

public class ServiceSelection {
    protected VBox root;
    private final Button confirmButton;
    private final Button cancelButton;
    private Logger logger;

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
    }

    // Getter per i bottoni
    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    // Metodo per mostrare la pagina
    public void display(Stage stage, NavigationService navigationService) {
        navigationService.display(stage, root,"Selezione Servizi e Attività");
    }
}
