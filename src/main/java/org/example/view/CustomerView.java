package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CustomerView {
    private VBox root;
    private VBox reservationsBox;
    private VBox customerInfoBox;
    private VBox reservationsContainer;
    private VBox passwordBox;
    private PasswordField oldPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Button changePasswordButton;

    public CustomerView() {
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Sezione dati cliente
        Label titleLabel = new Label("Dati Cliente");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Nome: ");
        Label surnameLabel = new Label("Cognome: ");
        Label birthDateLabel = new Label("Data di Nascita: ");
        Label fiscalCodeLabel = new Label("Codice Fiscale: ");
        Label emailLabel = new Label("Email: " );
        Label phoneLabel = new Label("Telefono: ");

        this.customerInfoBox = new VBox(5, titleLabel, nameLabel, surnameLabel, birthDateLabel, fiscalCodeLabel, emailLabel, phoneLabel);

        // Sezione prenotazioni
        Label reservationsLabel = new Label("Prenotazioni");
        reservationsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        reservationsBox = new VBox(5);

        this.reservationsContainer = new VBox(10, reservationsLabel, reservationsBox);
        reservationsContainer.setPadding(new Insets(10, 0, 10, 0));

        // Sezione cambio password
        Label passwordLabel = new Label("Modifica Password");
        passwordLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Vecchia Password");

        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Nuova Password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Conferma Nuova Password");

        changePasswordButton = new Button("Cambia Password");

        this.passwordBox = new VBox(5, passwordLabel, newPasswordField, confirmPasswordField, changePasswordButton);
        passwordBox.setVisible(false); // Inizialmente nascosto

        Button showPasswordFieldsButton = new Button("Modifica Password");
        showPasswordFieldsButton.setOnAction(e -> passwordBox.setVisible(true));

        VBox passwordContainer = new VBox(10, showPasswordFieldsButton, passwordBox);

        // Struttura principale
        root.getChildren().addAll(customerInfoBox, reservationsContainer, passwordContainer);
    }

    public VBox getRoot(){
        return root;
    }

    public VBox getCustomerInfoBox() {
        return customerInfoBox;
    }

    public VBox getPasswordBox(){
        return passwordBox;
    }

    public VBox getReservationsBox() {
        return reservationsBox;
    }

    public PasswordField getOldPasswordField() {
        return oldPasswordField;
    }

    public PasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public Button getChangePasswordButton() {
        return changePasswordButton;
    }
}