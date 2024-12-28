package org.example.factory;

import org.example.view.HomePage;
import org.example.view.ServiceSelection;

public abstract class GUIFactory {
    // Metodo per creare l'Home Page
    public abstract HomePage createHomePage();
    // Metodo per creare la schermata di selezione servizi inclusi nel soggiorno
    public abstract ServiceSelection createServiceSelection();
}
