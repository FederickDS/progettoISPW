package org.example.entity;

public class UserPreferences {

    private boolean isBlackAndWhite;
    private boolean saveToDatabase;

    public UserPreferences() {
        // Default: a colori e salvataggio in RAM
        this.isBlackAndWhite = false;
        this.saveToDatabase = false;
    }

    public boolean isBlackAndWhite() {
        return isBlackAndWhite;
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        isBlackAndWhite = blackAndWhite;
    }

    public boolean isSaveToDatabase() {
        return saveToDatabase;
    }

    public void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }
}
