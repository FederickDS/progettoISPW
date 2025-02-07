package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class CustomerViewAlternative {
    private VBox root;
    private VBox reservationsBox;
    private VBox customerInfoBox;
    private VBox reservationsContainer;
    private VBox passwordBox;
    private PasswordField oldPasswordField;
    private Text oldPasswordError;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Text newPasswordError;
    private Button changePasswordButton;
    private Button backButton;
    private static final String FX_STYLE = "-fx-font-size: 18px; -fx-font-weight: bold;";

    public CustomerViewAlternative() {
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label reservationLabel = new Label("I tuoi dati");
        reservationLabel.setStyle(FX_STYLE);

        // Sezione dati cliente
        Label titleLabel = new Label("Dati Cliente");
        titleLabel.setStyle(FX_STYLE);
        this.customerInfoBox = new VBox(5, titleLabel);

        // Sezione prenotazioni
        Label reservationsLabel = new Label("Prenotazioni");
        reservationsLabel.setStyle(FX_STYLE);
        reservationsBox = new VBox(5);
        reservationsBox.setPadding(new Insets(10));
        reservationsContainer = new VBox(10, reservationsLabel, reservationsBox);
        reservationsContainer.setPadding(new Insets(10, 0, 10, 0));

        // Sezione cambio password
        Label passwordLabel = new Label("Modifica Password");
        passwordLabel.setStyle(FX_STYLE);
        oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Vecchia Password");
        oldPasswordError = new Text("Password diversa da quella attuale");
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Nuova Password");
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Conferma Nuova Password");
        newPasswordError = new Text("Mettere la stessa password come conferma, tra gli 8 e i 16 caratteri");
        changePasswordButton = new Button("Cambia Password");
        passwordBox = new VBox(5, passwordLabel, oldPasswordField, oldPasswordError, newPasswordField, confirmPasswordField, newPasswordError, changePasswordButton);
        passwordBox.setVisible(false);
        Button showPasswordFieldsButton = new Button("Modifica Password");
        showPasswordFieldsButton.setOnAction(e -> showFirstPasswordBox());
        VBox passwordContainer = new VBox(10, showPasswordFieldsButton, passwordBox);

        backButton = new Button("Indietro");

        // Contenitore principale con scrollbar
        VBox contentBox = new VBox(20, customerInfoBox, reservationsContainer, passwordContainer);
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(600);

        root.getChildren().addAll(reservationLabel, scrollPane, backButton);
    }

    public void addReservation(String reservationInfo) {
        VBox reservationCard = new VBox(10);
        reservationCard.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #f0f0f0;");
        Label reservationDetails = new Label(reservationInfo);
        reservationCard.getChildren().add(reservationDetails);
        reservationsBox.getChildren().add(reservationCard);
    }

    public void showFirstPasswordBox(){
        passwordBox.setVisible(true);
        oldPasswordError.setVisible(false);
        newPasswordError.setVisible(false);
    }

    public void hideAllErrors(){
        oldPasswordError.setVisible(false);
        newPasswordError.setVisible(false);
    }

    public void showOldPasswordError(){
        oldPasswordError.setVisible(true);
    }

    public void showNewPasswordError(String message){
        if(!message.isEmpty() && !message.isBlank()){
            newPasswordError.setText(message);
        }
        newPasswordError.setVisible(true);
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

    public Button getBackButton() {
        return backButton;
    }
}
