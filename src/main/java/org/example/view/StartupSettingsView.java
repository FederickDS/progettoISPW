package org.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartupSettingsView{

    private RadioButton internalMemoryOption;
    private RadioButton databaseOption;
    private RadioButton colorInterfaceOption;
    private RadioButton bwInterfaceOption;
    private Button confirmButton;
    private Text optionUnselected;
    private Text interfaceUnselected;
    private VBox root;

    public StartupSettingsView() {
        // Layout principale centrato
        this.root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        this.root.setAlignment(Pos.CENTER);

        // Opzioni di memorizzazione
        Label storageLabel = new Label("Seleziona la modalità di memorizzazione:");
        this.internalMemoryOption = new RadioButton("Memoria Interna");
        this.databaseOption = new RadioButton("Database");
        ToggleGroup storageGroup = new ToggleGroup();
        this.internalMemoryOption.setToggleGroup(storageGroup);
        this.databaseOption.setToggleGroup(storageGroup);

        this.optionUnselected = new Text("Seleziona dove salvare i dati");
        this.optionUnselected.setVisible(false);
        this.optionUnselected.setManaged(false);

        // Opzioni dell'interfaccia grafica
        Label interfaceLabel = new Label("Seleziona la modalità dell'interfaccia grafica:");
        colorInterfaceOption = new RadioButton("A colori");
        bwInterfaceOption = new RadioButton("Bianco e nero");
        ToggleGroup interfaceGroup = new ToggleGroup();
        colorInterfaceOption.setToggleGroup(interfaceGroup);
        bwInterfaceOption.setToggleGroup(interfaceGroup);

        this.interfaceUnselected = new Text("Seleziona il tipo di interfaccia");
        this.interfaceUnselected.setVisible(false);
        this.interfaceUnselected.setManaged(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma");

        // Aggiunta degli elementi al layout principale
        this.root.getChildren().addAll(
                storageLabel,
                this.internalMemoryOption,
                this.databaseOption,
                this.optionUnselected,
                interfaceLabel,
                this.colorInterfaceOption,
                this.bwInterfaceOption,
                this.interfaceUnselected,
                this.confirmButton
        );
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

    public VBox getRoot() {return root;}
}
