package org.example.control;

import org.example.entity.StartupSettingsEntity;
import java.util.logging.Logger;

public class StartupSettingsSaving {
    Logger logger = Logger.getLogger(getClass().getName());

    public void saveSettings(String storageOption, String interfaceOption) {
        if (storageOption == null || interfaceOption == null) {
            throw new IllegalArgumentException("Entrambe le opzioni devono essere selezionate.");
        }

        // Otteniamo l'istanza Singleton
        StartupSettingsEntity settings = StartupSettingsEntity.getInstance();

        // Configuriamo i valori tramite i setter
        settings.setStorageOption(storageOption);
        settings.setInterfaceOption(interfaceOption);

        // Log delle informazioni salvate
        logger.info("Impostazioni salvate con successo: " + settings.toString());
    }

    public StartupSettingsEntity getSettings() {
        return StartupSettingsEntity.getInstance();
    }
}
