package org.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StartupSettingsView extends Application {
    private RadioButton internalMemoryOption;
    private RadioButton databaseOption;
    private RadioButton colorInterfaceOption;
    private RadioButton bwInterfaceOption;
    private Button confirmButton;

    @Override
    public void start(Stage stage) {
        // Layout principale
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F4F1E1;");

        // Form centrale
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10;");
        form.setMaxWidth(300);

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
        confirmButton.setStyle(
                "-fx-background-color: #2E3A8C; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        confirmButton.setOnMouseEntered(e -> confirmButton.setStyle(
                "-fx-background-color: #5F8FA6; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;"));
        confirmButton.setOnMouseExited(e -> confirmButton.setStyle(
                "-fx-background-color: #2E3A8C; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;"));

        // Aggiunta degli elementi al form
        form.getChildren().addAll(storageLabel, internalMemoryOption, databaseOption, interfaceLabel, colorInterfaceOption, bwInterfaceOption, confirmButton);

        // Aggiunta del form al centro
        root.setCenter(form);

        // Scena e stage
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Impostazioni Iniziali");
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
