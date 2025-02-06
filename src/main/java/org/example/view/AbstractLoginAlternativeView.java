package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AbstractLoginAlternativeView {
    private VBox root;
    private Label titleLabel;
    private TextField emailField;
    private Label emailErrorLabel;
    private PasswordField passwordField;
    private Label passwordErrorLabel;
    private RadioButton clientLoginOption;
    private RadioButton receptionistLoginOption;
    private ToggleGroup loginTypeGroup;
    private Button confirmButton;
    private Button cancelButton;
    private Text errorMessage;
    private Text registrationPrompt;
    private Button registerButton;

    protected AbstractLoginAlternativeView() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        titleLabel = new Label(getTitleText());
        titleLabel.getStyleClass().add("label");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailErrorLabel = new Label("Inserisci un'email valida");
        emailErrorLabel.setVisible(false);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordErrorLabel = new Label("La password non può essere vuota");
        passwordErrorLabel.setVisible(false);

        // Gruppo di selezione del tipo di login
        loginTypeGroup = new ToggleGroup();
        clientLoginOption = new RadioButton("Cliente");
        receptionistLoginOption = new RadioButton("Receptionist");
        clientLoginOption.setToggleGroup(loginTypeGroup);
        receptionistLoginOption.setToggleGroup(loginTypeGroup);

        this.errorMessage = new Text("Email o password non corrispondono a un account");
        this.errorMessage.setVisible(false);
        this.errorMessage.setManaged(false);

        confirmButton = new Button("Conferma");
        cancelButton = new Button("Annulla");

        registrationPrompt = new Text("Se non ti sei mai prenotato, puoi registrarti.");
        registrationPrompt.getStyleClass().add("registration-prompt");

        registerButton = new Button("Registrati");

        root.getChildren().addAll(titleLabel, emailField, emailErrorLabel, passwordField, passwordErrorLabel, clientLoginOption, receptionistLoginOption, errorMessage, confirmButton, cancelButton, registrationPrompt, registerButton);
    }

    protected abstract String getTitleText();

    public void hideErrorMessages() {
        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        errorMessage.setVisible(false);
    }

    public void showEmailError() {
        emailErrorLabel.setVisible(true);
    }

    public void showPasswordError() {
        passwordErrorLabel.setVisible(true);
    }

    public TextField getEmailField() {
        return emailField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public RadioButton getClientLoginOption() {
        return clientLoginOption;
    }

    public RadioButton getReceptionistLoginOption() {
        return receptionistLoginOption;
    }

    public ToggleGroup getLoginTypeGroup() {
        return loginTypeGroup;
    }

    public Button getConfirmButton() {
        return confirmButton;
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
    public void hideUserTypeToggle() {
        root.getChildren().remove(clientLoginOption);
        root.getChildren().remove(receptionistLoginOption);
    }
}
