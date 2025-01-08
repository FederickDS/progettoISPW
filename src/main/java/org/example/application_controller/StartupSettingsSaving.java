package org.example.application_controller;

import org.example.entity.StartupSettingsEntity;

public class StartupSettingsSaving {

    public void saveSettings(String storageOption, String interfaceOption) {
        if (storageOption == null || interfaceOption == null) {
            throw new IllegalArgumentException("Entrambe le opzioni devono essere selezionate.");
        }

        // Otteniamo l'istanza Singleton
        StartupSettingsEntity settings = StartupSettingsEntity.getInstance();

        // Configuriamo i valori tramite i setter
        settings.setStorageOption(storageOption);
        settings.setInterfaceOption(interfaceOption);

    }

    public StartupSettingsEntity getSettings() {
        return StartupSettingsEntity.getInstance();
    }
}
