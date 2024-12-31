package org.example.view.control;

import org.example.control.StartupSettingsSaving;
import org.example.view.StartupSettingsView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class StartupSettingsController {
    private final StartupSettingsView view;
    private final Stage stage;
    private final NavigationService navigationService;

    public StartupSettingsController(Stage stage, NavigationService navigationService) {
        this.stage = stage;
        this.navigationService = navigationService;
        view = new StartupSettingsView();
    }

    protected void loadStartupSettings(){
        view.start(this.stage);
        initController();
    }

    private void initController() {
        view.getConfirmButton().setOnAction(e -> handleConfirmButton());
    }

    private void handleConfirmButton() {
        String storageOption;
        String interfaceOption;

        // Verifica della selezione della modalità di memorizzazione
        if (view.getInternalMemoryOption().isSelected()) {
            storageOption = "stateless";
        } else if (view.getDatabaseOption().isSelected()) {
            storageOption = "database";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seleziona dove salvare i dati.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Verifica della selezione della modalità dell'interfaccia
        if (view.getColorInterfaceOption().isSelected()) {
            interfaceOption = "color";
        } else if (view.getBwInterfaceOption().isSelected()) {
            interfaceOption = "BW";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seleziona i possibili tipi di interfacce", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Salvataggio delle impostazioni nell'entità
        StartupSettingsSaving settingsSaving = new StartupSettingsSaving();
        settingsSaving.saveSettings(storageOption, interfaceOption);

        // Passaggio del controllo a HomePageController
        navigationService.navigateToHomePage();
    }
}
