package org.example.view.control;

import org.example.control.StartupSettingsSaving;
import org.example.view.StartupSettingsView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class StartupSettingsController {
    private final StartupSettingsView view;
    private final NavigationManager navigationManager;

    public StartupSettingsController(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        view = new StartupSettingsView();
        view.start(navigationManager.getStage());
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
        navigationManager.navigateToHomePage();
    }
}
