package org.example.view;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ClientRegistrationView extends AbstractRegistrationView {
    private TextField birthDateField;
    private Text birthDateError;
    private TextField taxCodeField;
    private Text taxCodeError;
    public ClientRegistrationView() {
        super();
    }

    @Override
    protected void addSpecificFields() {
        // Campi specifici per i clienti
        birthDateField = new TextField();
        birthDateField.setPromptText("Data di nascita (yyyy-mm-dd)");

        birthDateError = new Text();
        birthDateError.getStyleClass().add("error");
        birthDateError.setVisible(false);

        taxCodeField = new TextField();
        taxCodeField.setPromptText("Codice fiscale");

        taxCodeError = new Text();
        taxCodeError.getStyleClass().add("error");
        taxCodeError.setVisible(false);

        // Aggiungi campi specifici al layout
        root.getChildren().addAll(birthDateField, birthDateError, taxCodeField, taxCodeError);
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

    public void showBirthDateError(String message) {
        birthDateError.setText(message);
        birthDateError.setVisible(true);
    }

    public TextField getTaxCodeField() {
        return taxCodeField;
    }

    public void showTaxCodeError(String message) {
        taxCodeError.setText(message);
        taxCodeError.setVisible(true);
    }

    @Override
    public void hideSpecificErrors(){
        birthDateError.setVisible(false);
        taxCodeError.setVisible(false);
    }
}
