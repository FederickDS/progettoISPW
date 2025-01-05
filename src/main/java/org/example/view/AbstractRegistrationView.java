package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AbstractRegistrationView {
    protected VBox root;

    protected Label titleLabel;
    protected TextField firstNameField;
    protected TextField lastNameField;
    protected TextField emailField;
    protected PasswordField passwordField;
    protected PasswordField repeatPasswordField;
    protected Button registerButton;
    protected Button cancelButton;
    protected Text loginPrompt;
    protected Button loginButton;

    public AbstractRegistrationView() {
        // Layout di base
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        //risolve code smell
        String bottone ="button";

        // Campi comuni
        titleLabel = new Label(getTitleText());
        titleLabel.getStyleClass().add("label");

        firstNameField = new TextField();
        firstNameField.setPromptText("Nome");

        lastNameField = new TextField();
        lastNameField.setPromptText("Cognome");

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Repeat Password");

        registerButton = new Button("Registrati");
        registerButton.getStyleClass().add(bottone);

        cancelButton = new Button("Annulla");
        cancelButton.getStyleClass().add(bottone);


        // Aggiungi campi alla root
        root.getChildren().addAll(titleLabel,firstNameField, lastNameField, emailField, passwordField, repeatPasswordField);

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

    public VBox getVBox() {
        return root;
    }
}
