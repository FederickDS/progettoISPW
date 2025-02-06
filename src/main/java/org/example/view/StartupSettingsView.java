package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class StartupSettingsView{

    private RadioButton internalMemoryOption;
    private RadioButton databaseOption;
    private RadioButton fileOption;
    private RadioButton colorInterfaceOption;
    private RadioButton bwInterfaceOption;
    private RadioButton mainInterfaceOption;    // Opzione per interfaccia principale
    private RadioButton secondaryInterfaceOption;  // Opzione per interfaccia secondaria
    private Button confirmButton;
    private Text optionUnselected;
    private Text interfaceUnselected;
    private Text interfaceTypeUnselected;
    private VBox root;

    public StartupSettingsView() {
        this.root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        this.root.setAlignment(Pos.CENTER);

        // Opzioni di memorizzazione
        Label storageLabel = new Label("Seleziona la modalità di memorizzazione:");
        this.internalMemoryOption = new RadioButton("Memoria Interna");
        this.databaseOption = new RadioButton("Database");
        this.fileOption = new RadioButton("File Locale");

        ToggleGroup storageGroup = new ToggleGroup();
        this.internalMemoryOption.setToggleGroup(storageGroup);
        this.databaseOption.setToggleGroup(storageGroup);
        this.fileOption.setToggleGroup(storageGroup);

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

        // Nuova selezione: Tipo di interfaccia
        Label interfaceTypeLabel = new Label("Seleziona l'interfaccia:");
        mainInterfaceOption = new RadioButton("Principale");
        secondaryInterfaceOption = new RadioButton("Secondaria");
        ToggleGroup interfaceTypeGroup = new ToggleGroup();
        mainInterfaceOption.setToggleGroup(interfaceTypeGroup);
        secondaryInterfaceOption.setToggleGroup(interfaceTypeGroup);

        this.interfaceTypeUnselected = new Text("Seleziona l'interfaccia da utilizzare");
        this.interfaceTypeUnselected.setVisible(false);
        this.interfaceTypeUnselected.setManaged(false);

        // Bottone di conferma
        confirmButton = new Button("Conferma");

        // Aggiunta degli elementi al layout principale
        this.root.getChildren().addAll(
                storageLabel,
                this.internalMemoryOption,
                this.databaseOption,
                this.fileOption,
                this.optionUnselected,
                interfaceLabel,
                this.colorInterfaceOption,
                this.bwInterfaceOption,
                this.interfaceUnselected,
                interfaceTypeLabel,
                this.mainInterfaceOption,
                this.secondaryInterfaceOption,
                this.interfaceTypeUnselected,
                this.confirmButton
        );
    }

    public RadioButton getInternalMemoryOption() {
        return internalMemoryOption;
    }

    public RadioButton getDatabaseOption() {
        return databaseOption;
    }

    public RadioButton getFileOption() {
        return fileOption;
    }

    public RadioButton getColorInterfaceOption() {
        return colorInterfaceOption;
    }

    public RadioButton getBwInterfaceOption() {
        return bwInterfaceOption;
    }

    public RadioButton getMainInterfaceOption() {
        return mainInterfaceOption;
    }

    public RadioButton getSecondaryInterfaceOption() {
        return secondaryInterfaceOption;
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

    public void showInterfaceTypeUnselected() {
        this.interfaceTypeUnselected.setVisible(true);
        this.interfaceTypeUnselected.setManaged(true);
    }

    public VBox getRoot() {
        return root;
    }
}
