package org.example.entity;

import java.io.Serializable;

public class StartupSettingsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String storageOption;
    private String interfaceOption;

    // Istanza statica unica
    private static StartupSettingsEntity instance;

    // Costruttore privato per impedire l'instanziazione diretta
    private StartupSettingsEntity() {
    }

    // Metodo pubblico per ottenere l'istanza Singleton
    public static StartupSettingsEntity getInstance() {
        if (instance == null) {
            synchronized (StartupSettingsEntity.class) {
                if (instance == null) {
                    instance = new StartupSettingsEntity();
                }
            }
        }
        return instance;
    }

    // Getters e Setters
    public String getStorageOption() {
        return storageOption;
    }

    public void setStorageOption(String storageOption) {
        this.storageOption = storageOption;
    }

    public String getInterfaceOption() {
        return interfaceOption;
    }

    public void setInterfaceOption(String interfaceOption) {
        this.interfaceOption = interfaceOption;
    }

    @Override
    public String toString() {
        return "StartupSettingsEntity{" +
                "storageOption='" + storageOption + '\'' +
                ", interfaceOption='" + interfaceOption + '\'' +
                '}';
    }
}
