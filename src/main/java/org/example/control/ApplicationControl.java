package org.example.control;

import org.example.entity.UserPreferences;

public class ApplicationControl {

    private UserPreferences UserPreferences;

    public ApplicationControl() {
        UserPreferences = new UserPreferences(); // Inizializzazione delle preferenze
    }

    public void handleUserPreferences(boolean isBlackAndWhite, boolean saveToDatabase) {
        // Aggiorna l'entity con le preferenze dell'utente
        UserPreferences.setBlackAndWhite(isBlackAndWhite);
        UserPreferences.setSaveToDatabase(saveToDatabase);

        // Simula azioni in base alle preferenze
        System.out.println("Preferenze aggiornate:");
        System.out.println("Schermate in bianco e nero: " + isBlackAndWhite);
        System.out.println("Salvataggio su database: " + saveToDatabase);
    }

    public UserPreferences getUserPreferences() {
        return UserPreferences;
    }
}
