package org.example.control;

import org.example.entity.StartupSettingsEntity;
import org.example.view.StartupSettingsView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class StartupSettingsController {
    private final StartupSettingsView view;
    Logger logger = Logger.getLogger(getClass().getName());

    public StartupSettingsController(Stage stage) {
        view = new StartupSettingsView();
        view.start(stage);
        initController();
    }

    private void initController() {
        view.getConfirmButton().setOnAction(e -> handleConfirmButton());
    }
    private void handleConfirmButton() {
        String storageOption = view.getInternalMemoryOption().isSelected() ? "Memoria Interna" :
                view.getDatabaseOption().isSelected() ? "Database" : null;

        String interfaceOption = view.getColorInterfaceOption().isSelected() ? "A colori" :
                view.getBwInterfaceOption().isSelected() ? "Bianco e Nero" : null;

        if (storageOption == null || interfaceOption == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Per favore seleziona entrambe le opzioni.", ButtonType.OK);
            alert.showAndWait();
        } else {
            StartupSettingsSaving settingsSaving = new StartupSettingsSaving();
            settingsSaving.saveSettings(storageOption, interfaceOption);

            // Usa l'entità salvata per ulteriori operazioni
            StartupSettingsEntity savedSettings = settingsSaving.getSettings();
            logger.info("Dati salvati: " + savedSettings);
        }
    }
}
