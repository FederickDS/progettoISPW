package org.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.entity.StartupSettingsEntity;

public class StartupSettingsView extends Application {

    private RadioButton internalMemoryOption;
    private RadioButton databaseOption;
    private RadioButton colorInterfaceOption;
    private RadioButton bwInterfaceOption;
    private Button confirmButton;

    @Override
    public void start(Stage stage) {
        // Layout principale centrato
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER); // Allinea tutto al centro
        root.setPadding(new Insets(20));

        // Opzioni di memorizzazione
        Label storageLabel = new Label("Seleziona la modalità di memorizzazione:");
        internalMemoryOption = new RadioButton("Memoria Interna");
        databaseOption = new RadioButton("Database");
        ToggleGroup storageGroup = new ToggleGroup();
        internalMemoryOption.setToggleGroup(storageGroup);
        databaseOption.setToggleGroup(storageGroup);

        // Opzioni dell'interfaccia grafica
        Label interfaceLabel = new Label("Seleziona la modalità dell'interfaccia grafica:");
        colorInterfaceOption = new RadioButton("A colori");
        bwInterfaceOption = new RadioButton("Bianco e nero");
        ToggleGroup interfaceGroup = new ToggleGroup();
        colorInterfaceOption.setToggleGroup(interfaceGroup);
        bwInterfaceOption.setToggleGroup(interfaceGroup);

        // Bottone di conferma
        confirmButton = new Button("Conferma");

        // Aggiunta degli elementi al layout principale
        root.getChildren().addAll(storageLabel, internalMemoryOption, databaseOption, interfaceLabel, colorInterfaceOption, bwInterfaceOption, confirmButton);

        // Scena e stage
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/style/color-mode.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Impostazioni Iniziali");
        stage.centerOnScreen();
        stage.setFullScreen(true);
        stage.show();
    }

    public RadioButton getInternalMemoryOption() {
        return internalMemoryOption;
    }

    public RadioButton getDatabaseOption() {
        return databaseOption;
    }

    public RadioButton getColorInterfaceOption() {
        return colorInterfaceOption;
    }

    public RadioButton getBwInterfaceOption() {
        return bwInterfaceOption;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }
}