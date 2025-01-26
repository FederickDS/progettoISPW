package org.example.view;

public class EssentialInfoView extends AbstractRegistrationView {

    @Override
    protected void addSpecificFields() {
        // Nessun campo specifico aggiuntivo per questa vista
    }

    @Override
    public void hideSpecificErrors() {
        // Nessun errore specifico da gestire
    }

    @Override
    public String getTitleText() {
        return "Inserisci Informazioni Essenziali";
    }

    @Override
    public String getType() {
        return "essentialInfo";
    }

    // Rimuoviamo i campi superflui della vista base
    public void hideBasicFields() {
        root.getChildren().removeAll(passwordField, passwordError, repeatPasswordField, repeatPasswordError);
    }
}
