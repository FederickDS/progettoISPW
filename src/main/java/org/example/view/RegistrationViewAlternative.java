package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class RegistrationViewAlternative {
    protected VBox root;

    protected Text databaseError;
    protected Label titleLabel;
    protected TextField firstNameField;
    protected Text firstNameError;
    protected TextField lastNameField;
    protected Text lastNameError;
    protected TextField emailField;
    protected Text emailError;
    protected TextField phoneNumberField;
    protected Text phoneNumberError;
    protected PasswordField passwordField;
    protected Text passwordError;
    protected PasswordField repeatPasswordField;
    protected Text repeatPasswordError;
    protected Button confirmButton;
    protected Button backButton;
    protected Button loginButton;
    protected ToggleGroup userTypeGroup;
    protected RadioButton clientOption;
    protected RadioButton receptionistOption;
    protected VBox userTypeBox;

    protected RegistrationViewAlternative() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        String error = "error";

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

        phoneNumberError = new Text("Numero già in uso.");
        phoneNumberError.getStyleClass().add(error);
        phoneNumberError.setVisible(false);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        passwordError = new Text("Inserisci una password valida");
        passwordError.getStyleClass().add(error);
        passwordError.setVisible(false);

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Ripeti Password");

        repeatPasswordError = new Text("Le password non coincidono");
        repeatPasswordError.getStyleClass().add(error);
        repeatPasswordError.setVisible(false);

        userTypeGroup = new ToggleGroup();
        clientOption = new RadioButton("Cliente");
        clientOption.setToggleGroup(userTypeGroup);
        receptionistOption = new RadioButton("Receptionist");
        receptionistOption.setToggleGroup(userTypeGroup);
        clientOption.setSelected(true);

        userTypeBox = new VBox(10, new Label("Seleziona il tipo di utente:"), clientOption, receptionistOption);

        confirmButton = new Button("Conferma");
        confirmButton.getStyleClass().add("button");

        backButton = new Button("Indietro");
        backButton.getStyleClass().add("button");

        loginButton = new Button("Accedi");
        loginButton.getStyleClass().add("button");

        root.getChildren().addAll(titleLabel, firstNameField, firstNameError, lastNameField, lastNameError, emailField, emailError, phoneNumberField, phoneNumberError, passwordField, passwordError, repeatPasswordField, repeatPasswordError, userTypeBox, confirmButton, backButton, loginButton);
    }

    protected abstract String getTitleText();
    public abstract void hideSpecificErrors();
    public VBox getRoot() {
        return root;
    }
    public String getSelectedUserType() {
        return clientOption.isSelected() ? "client" : "receptionist";
    }
    public Text getDatabaseError() {
        return databaseError;
    }
    public Text getPhoneNumberError() {
        return phoneNumberError;
    }
    public Button getConfirmButton() {
        return confirmButton;
    }
    public TextField getFirstNameField() { return firstNameField; }
    public Text getFirstNameError() { return firstNameError; }
    public TextField getLastNameField() { return lastNameField; }
    public Text getLastNameError() { return lastNameError; }
    public TextField getEmailField() { return emailField; }
    public Text getEmailError() { return emailError; }
    public TextField getPhoneNumberField() { return phoneNumberField; }
    public PasswordField getPasswordField() { return passwordField; }
    public Text getPasswordError() { return passwordError; }
    public PasswordField getRepeatPasswordField() { return repeatPasswordField; }
    public Text getRepeatPasswordError() { return repeatPasswordError; }
    public Button getBackButton() { return backButton; }
    public Button getLoginButton() { return loginButton; }
    public void hideUserTypeToggle() {
        root.getChildren().remove(userTypeBox);
    }
}