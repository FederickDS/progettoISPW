package org.example.entity;

import java.io.Serializable;

public class StartupSettingsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String storageOption;
    private String interfaceOption;

    // Costruttore privato per impedire l'instanziazione diretta
    private StartupSettingsEntity() {
    }
    //correzione di Sonacloud per corretta gestione di una classe Singleton: istanza statica unica
    private static class SingletonHelper {
        private static final StartupSettingsEntity INSTANCE = new StartupSettingsEntity();
    }

    // Metodo pubblico per ottenere l'istanza Singleton
    public static StartupSettingsEntity getInstance() {
        return SingletonHelper.INSTANCE;
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
