package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.StartupSettingsSaving;
import org.example.view.StartupSettingsView;

public class StartupSettingsController {
    private final StartupSettingsView view;

    public StartupSettingsController() {
        this.view = new StartupSettingsView();
        view.getConfirmButton().setOnAction(e -> handleConfirmButton());
    }

    public VBox getView() {
        return view.getRoot();
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
            view.showOptionUnselected();
            return;
        }

        // Verifica della selezione della modalità dell'interfaccia
        if (view.getColorInterfaceOption().isSelected()) {
            interfaceOption = "color";
        } else if (view.getBwInterfaceOption().isSelected()) {
            interfaceOption = "BW";
        } else {
            view.showInterfaceUnselected();
            return;
        }

        // Salvataggio delle impostazioni nell'entità
        StartupSettingsSaving settingsSaving = new StartupSettingsSaving();
        settingsSaving.saveSettings(storageOption, interfaceOption);

        // Passaggio del controllo a HomePageController
        NavigationManager.getInstance().navigateToHomePage();
    }
}
