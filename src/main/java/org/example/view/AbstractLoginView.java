package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AbstractLoginView {
    private VBox root;
    private Label titleLabel;
    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button cancelButton;
    private Text errorMessage;
    private Text registrationPrompt;
    private Button registerButton;

    protected AbstractLoginView() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.getStyleClass().add("root");
        //risolve code smell
        String button ="button";


        titleLabel = new Label(getTitleText());
        titleLabel.getStyleClass().add("label");

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        this.errorMessage = new Text("Email o password non corrispondono a un account");
        this.errorMessage.setVisible(false); // Non visibile inizialmente
        this.errorMessage.setManaged(false); // Non occupa spazio nel layout

        loginButton = new Button("Conferma");
        loginButton.getStyleClass().add(button);

        cancelButton = new Button("Annulla");
        cancelButton.getStyleClass().add(button);

        // Aggiunta del prompt per la registrazione
        registrationPrompt = new Text("Se non ti sei mai prenotato, puoi registrarti.");
        registrationPrompt.getStyleClass().add("registration-prompt");

        // Aggiunta del pulsante di registrazione
        registerButton = new Button("Registrati");
        registerButton.getStyleClass().add(button);

        root.getChildren().addAll(titleLabel, emailField, passwordField, errorMessage, loginButton, cancelButton, registrationPrompt, registerButton);
    }

    protected abstract String getTitleText();
    public abstract String getType();

    public TextField getEmailField() {
        return emailField;
    }

    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public VBox getRoot() {
        return root;
    }

    public Text getErrorMessage() {
        return errorMessage;
    }

    public Text getRegistrationPrompt() {
        return registrationPrompt;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}
