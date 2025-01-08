package org.example.view;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ClientRegistrationView extends AbstractRegistrationView {
    private TextField birthDateField;
    private TextField taxCodeField;
    private TextField phoneNumberField;
    protected Text phoneErrorMessage;

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

        phoneErrorMessage = new Text("Il numero di telefono inserito e'stato utilizzato per un altro utente.");
        phoneErrorMessage.getStyleClass().add("error-message");
        phoneErrorMessage.setVisible(false);
        // Aggiungi campi specifici al layout
        root.getChildren().addAll(birthDateField, taxCodeField, phoneNumberField, phoneErrorMessage);
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

    public Text getPhoneErrorMessage() {return phoneErrorMessage;}

    @Override
    public void showPhoneErrorMessage(String message) {
        phoneErrorMessage.setText(message);
        phoneErrorMessage.setVisible(true);
    }

}
