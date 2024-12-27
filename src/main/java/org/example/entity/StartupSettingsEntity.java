package org.example.entity;

import org.example.factory.GUIBw;
import org.example.factory.GUIColored;
import org.example.factory.GUIFactory;

import java.io.Serializable;

public class StartupSettingsEntity implements Serializable {

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

    // Metodo per determinare il tipo di GUI
    public GUIFactory typeOfGUI() {
        if ("BW".equalsIgnoreCase(getInstance().interfaceOption)) {
            return new GUIBw();
        } else if ("color".equalsIgnoreCase(getInstance().interfaceOption)) {
            return new GUIColored();
        } else {
            throw new IllegalArgumentException("Tipo di GUI non supportato: " + getInstance().interfaceOption);
        }
    }
}
