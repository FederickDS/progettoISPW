package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AbstractRegistrationView {
    protected VBox root;

    protected Text databaseError;
    protected Label titleLabel;
    protected TextField firstNameField;
    protected Text firstNameError;
    protected TextField lastNameField;
    protected Text lastNameError;
    protected TextField emailField;
    protected Text emailError;
    private TextField phoneNumberField;
    protected Text phoneNumberError;
    protected PasswordField passwordField;
    protected Text passwordError;
    protected PasswordField repeatPasswordField;
    protected Text repeatPasswordError;
    protected Button registerButton;
    protected Button cancelButton;
    protected Text loginPrompt;
    protected Button loginButton;

    protected AbstractRegistrationView() {
        // Layout di base
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        //risolve code smell
        String bottone ="button";
        String error = "error";

        // Campi comuni
        databaseError = new Text();
        databaseError.getStyleClass().add(error);
        databaseError.setVisible(false);

        titleLabel = new Label(getTitleText());
        titleLabel.getStyleClass().add("label");

        firstNameField = new TextField();
        firstNameField.setPromptText("Nome");

        firstNameError = new Text("Nome non può essere nullo");
        firstNameError.getStyleClass().add(error);
        firstNameError.setVisible(false);

        lastNameField = new TextField();
        lastNameField.setPromptText("Cognome");

        lastNameError = new Text("Cognome non può essere nullo");
        lastNameError.getStyleClass().add(error);
        lastNameError.setVisible(false);

        emailField = new TextField();
        emailField.setPromptText("Email");

        emailError = new Text("Inserisci una email");
        emailError.getStyleClass().add(error);
        emailError.setVisible(false);

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Numero di telefono");

        phoneNumberError = new Text("Il numero di telefono inserito e'stato utilizzato per un altro utente.");
        phoneNumberError.getStyleClass().add("error-message");
        phoneNumberError.setVisible(false);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        passwordError = new Text("Inserisci una password dagli 8 ai 16 caratteri");
        passwordError.getStyleClass().add(error);
        passwordError.setVisible(false);

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Repeat Password");

        repeatPasswordError = new Text("Campo diverso dalla password");
        repeatPasswordError.getStyleClass().add(error);
        repeatPasswordError.setVisible(false);

        registerButton = new Button("Registrati");
        registerButton.getStyleClass().add(bottone);

        cancelButton = new Button("Annulla");
        cancelButton.getStyleClass().add(bottone);


        // Aggiungi campi alla root
        root.getChildren().addAll(titleLabel,firstNameField, firstNameError, lastNameField, lastNameError, emailField, emailError, phoneNumberField, phoneNumberError, passwordField, passwordError, repeatPasswordField, repeatPasswordError);

        addSpecificFields();

        root.getChildren().add(registerButton);
        root.getChildren().add(cancelButton);

        // Aggiunta del prompt per il login
        loginPrompt = new Text("Hai già un account? Accedi qui.");
        loginPrompt.getStyleClass().add("login-prompt");
        root.getChildren().add(loginPrompt);

        // Aggiunta del pulsante per il login
        loginButton = new Button("Accedi");
        loginButton.getStyleClass().add(bottone);
        root.getChildren().add(loginButton);
    }

    protected abstract void addSpecificFields();
    public abstract void hideSpecificErrors();
    protected abstract String getTitleText();
    public abstract String getType();


    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public PasswordField getRepeatPasswordField() {
        return repeatPasswordField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void showFirstNameError() {
        firstNameError.setVisible(true);
    }

    public void hideAllErrors() {
        firstNameError.setVisible(false);
        lastNameError.setVisible(false);
        emailError.setVisible(false);
        passwordError.setVisible(false);
        repeatPasswordError.setVisible(false);
    }

    public void showLastNameError() {
        lastNameError.setVisible(true);
    }

    public void showEmailError() {
        emailError.setVisible(true);
    }

    public void showPasswordError(String message) {
        passwordError.setText(message);
        passwordError.setVisible(true);
    }

    public void showRepeatPasswordError(String message) {
        repeatPasswordError.setText(message);
        repeatPasswordError.setVisible(true);
    }

    public VBox getRoot() {
        return root;
    }

    public void showPhoneNumberError(String message) {
        phoneNumberError.setText(message);
        phoneNumberError.setVisible(true);
    }

    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public void showDatabaseError(String message) {
        databaseError.setText(message);
        databaseError.setVisible(true);
    }

}
