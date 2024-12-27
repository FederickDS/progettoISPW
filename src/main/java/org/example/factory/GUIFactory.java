package org.example.factory;

import org.example.view.HomePage;

public abstract class GUIFactory {
    // Metodo per determinare il tipo di GUI
    public static GUIFactory typeOfGUI(String guiType) {
        if ("BW".equalsIgnoreCase(guiType)) {
            return new GUIBW();
        } else if ("color".equalsIgnoreCase(guiType)) {
            return new GUIColored();
        } else {
            throw new IllegalArgumentException("Tipo di GUI non supportato: " + guiType);
        }
    }

    // Metodo per creare l'Home Page
    public abstract HomePage createHomePage();
}
