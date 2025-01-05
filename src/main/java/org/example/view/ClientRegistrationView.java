package org.example.view;

import javafx.scene.control.TextField;

public class ClientRegistrationView extends AbstractRegistrationView {
    private TextField birthDateField;
    private TextField taxCodeField;
    private TextField phoneNumberField;

    public ClientRegistrationView() {
        super();
    }

    @Override
    protected void addSpecificFields() {
        // Campi specifici per i clienti
        birthDateField = new TextField();
        birthDateField.setPromptText("Data di nascita (yyyy-mm-dd)");

        taxCodeField = new TextField();
        taxCodeField.setPromptText("Codice fiscale");

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Numero di telefono");

        // Aggiungi campi specifici al layout
        root.getChildren().addAll(birthDateField, taxCodeField, phoneNumberField);
    }

    @Override
    protected String getTitleText() {
        return "Registrati come Cliente per prenotare la stanza";
    }
    @Override
    public String getType(){
        return "client";
    }


    public TextField getBirthDateField() {
        return birthDateField;
    }

    public TextField getTaxCodeField() {
        return taxCodeField;
    }

    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }
}
