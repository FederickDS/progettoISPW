package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EssentialInfoViewAlternative {
    protected VBox root;
    protected Label titleLabel;
    protected TextField firstNameField;
    protected Text firstNameError;
    protected TextField lastNameField;
    protected Text lastNameError;
    protected TextField emailField;
    protected Text emailError;
    protected TextField phoneNumberField;
    protected Text phoneNumberError;
    protected Button confirmButton;
    protected Button backButton;

    public EssentialInfoViewAlternative() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        String error = "error";

        titleLabel = new Label("Inserisci Informazioni Essenziali");
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

        confirmButton = new Button("Conferma");
        confirmButton.getStyleClass().add("button");

        backButton = new Button("Indietro");
        backButton.getStyleClass().add("button");

        root.getChildren().addAll(titleLabel, firstNameField, firstNameError, lastNameField, lastNameError, emailField, emailError, phoneNumberField, phoneNumberError, confirmButton, backButton);
    }

    public VBox getRoot() {
        return root;
    }

    public TextField getFirstNameField() { return firstNameField; }
    public Text getFirstNameError() { return firstNameError; }
    public TextField getLastNameField() { return lastNameField; }
    public Text getLastNameError() { return lastNameError; }
    public TextField getEmailField() { return emailField; }
    public Text getEmailError() { return emailError; }
    public TextField getPhoneNumberField() { return phoneNumberField; }
    public Text getPhoneNumberError() { return phoneNumberError; }
    public Button getConfirmButton() { return confirmButton; }
    public Button getBackButton() { return backButton; }
}
