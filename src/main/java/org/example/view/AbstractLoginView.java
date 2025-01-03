package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.view.control.NavigationService;

public abstract class AbstractLoginView {
    private Stage stage;
    private Scene scene;
    private VBox root;
    private Label titleLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button cancelButton;
    private Text errorMessage;

    protected AbstractLoginView(Stage stage) {
        this.stage = stage;
        root = new VBox(10);
        root.getStyleClass().add("root");

        titleLabel = new Label(getTitleText());
        titleLabel.getStyleClass().add("label");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        this.errorMessage = new Text("Username o password non corrispondono a un account");
        this.errorMessage.setVisible(false); // Non visibile inizialmente
        this.errorMessage.setManaged(false); // Non occupa spazio nel layout


        loginButton = new Button("Conferma");
        loginButton.getStyleClass().add("button");

        cancelButton = new Button("Annulla");
        cancelButton.getStyleClass().add("button");

        root.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, cancelButton);
        scene = new Scene(root, 1280, 720);
    }

    protected abstract String getTitleText();
    public abstract String getType();

    // Metodo per mostrare la pagina
    public void display(Stage stage, NavigationService navigationService) {
        navigationService.display(stage, root,"Seleziona date e persone");
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
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

    public VBox getVBox(){return root;}

    public Text getErrorMessage() {return errorMessage;}
}
