package org.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class StartupSettingsView extends Application {

    private RadioButton internalMemoryOption;
    private RadioButton databaseOption;
    private RadioButton colorInterfaceOption;
    private RadioButton bwInterfaceOption;
    private Button confirmButton;
    private Text optionUnselected;
    private Text interfaceUnselected;

    @Override
    public void start(Stage stage) {
        // Layout principale centrato
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER); // Allinea tutto al centro
        root.setPadding(new Insets(20));

        // Opzioni di memorizzazione
        Label storageLabel = new Label("Seleziona la modalità di memorizzazione:");
        this.internalMemoryOption = new RadioButton("Memoria Interna");
        this.databaseOption = new RadioButton("Database");
        ToggleGroup storageGroup = new ToggleGroup();
        this.internalMemoryOption.setToggleGroup(storageGroup);
        this.databaseOption.setToggleGroup(storageGroup);

        this.optionUnselected = new Text("Seleziona dove salvare i dati");
        this.optionUnselected.setVisible(false); // Non visibile inizialmente
        this.optionUnselected.setManaged(false); // Non occupa spazio nel layout

        // Opzioni dell'interfaccia grafica
        Label interfaceLabel = new Label("Seleziona la modalità dell'interfaccia grafica:");
        colorInterfaceOption = new RadioButton("A colori");
        bwInterfaceOption = new RadioButton("Bianco e nero");
        ToggleGroup interfaceGroup = new ToggleGroup();
        colorInterfaceOption.setToggleGroup(interfaceGroup);
        bwInterfaceOption.setToggleGroup(interfaceGroup);

        this.interfaceUnselected = new Text("Seleziona il tipo di interfaccia");
        this.interfaceUnselected.setVisible(false); // Non visibile inizialmente
        this.interfaceUnselected.setManaged(false); // Non occupa spazio nel layout

        // Bottone di conferma
        confirmButton = new Button("Conferma");

        // Aggiunta degli elementi al layout principale
        root.getChildren().addAll(storageLabel, this.internalMemoryOption, this.databaseOption, this.optionUnselected, interfaceLabel, this.colorInterfaceOption, this.bwInterfaceOption, this.interfaceUnselected, this.confirmButton);

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

    public void showOptionUnselected() {
        this.optionUnselected.setVisible(true);
        this.optionUnselected.setManaged(true);
    }

    public void showInterfaceUnselected() {
        this.interfaceUnselected.setVisible(true);
        this.interfaceUnselected.setManaged(true);
    }

}