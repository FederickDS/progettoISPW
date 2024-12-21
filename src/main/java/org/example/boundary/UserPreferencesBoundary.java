package org.example.boundary;

import org.example.control.ApplicationControl;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPreferencesBoundary extends Application{

    private ApplicationControl control = new ApplicationControl();

    @Override
    public void start(Stage primaryStage) {
        // Creazione dei componenti grafici
        Label label = new Label("Impostazioni iniziali");

        // Opzione per bianco e nero
        ToggleGroup colorToggle = new ToggleGroup();
        RadioButton bwOption = new RadioButton("Bianco e Nero");
        RadioButton colorOption = new RadioButton("A Colori");
        bwOption.setToggleGroup(colorToggle);
        colorOption.setToggleGroup(colorToggle);

        // Opzione per salvataggio
        ToggleGroup storageToggle = new ToggleGroup();
        RadioButton dbOption = new RadioButton("Database");
        RadioButton ramOption = new RadioButton("RAM");
        dbOption.setToggleGroup(storageToggle);
        ramOption.setToggleGroup(storageToggle);

        Button submitButton = new Button("Conferma");

        // Azione al click del pulsante
        submitButton.setOnAction(e -> {
            boolean isBlackAndWhite = bwOption.isSelected();
            boolean saveToDatabase = dbOption.isSelected();
            control.handleUserPreferences(isBlackAndWhite, saveToDatabase);
            primaryStage.close(); // Chiude la finestra iniziale
        });

        // Layout
        VBox layout = new VBox(10, label, bwOption, colorOption, dbOption, ramOption, submitButton);
        layout.setAlignment(Pos.CENTER);

        // Scena
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Impostazioni Applicazione");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
