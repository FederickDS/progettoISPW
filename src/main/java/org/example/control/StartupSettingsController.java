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
        String storageOption = null;
        String interfaceOption = null;
        if (view.getInternalMemoryOption().isSelected()){
            storageOption = "stateless";
        }else if (view.getDatabaseOption().isSelected()) {
            storageOption = "database";
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seleziona dove salvare i dati.", ButtonType.OK);
            alert.showAndWait();
        }

        if (view.getColorInterfaceOption().isSelected()){
            interfaceOption = "color";
        }else if (view.getBwInterfaceOption().isSelected()) {
            interfaceOption = "BW";
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seleziona i possibili tipi di interfacce", ButtonType.OK);
            alert.showAndWait();
        }


        StartupSettingsSaving settingsSaving = new StartupSettingsSaving();
        settingsSaving.saveSettings(storageOption, interfaceOption);

        // Usa l'entità salvata per ulteriori operazioni
        StartupSettingsEntity savedSettings = settingsSaving.getSettings();
        logger.info("Dati correttamente salvati ");
    }
}
