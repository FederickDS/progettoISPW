package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RoomBookingOptionsView {
    private final VBox root;
    private final Button loginButton;
    private final Button registerButton;
    private final Button essentialInfoButton;

    public RoomBookingOptionsView() {
        // Layout della pagina
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Titolo e descrizione
        Label title = new Label("Seleziona un'opzione per prenotare la stanza");
        Label description = new Label("Scegli se accedere, registrarti o inserire solo le informazioni essenziali.");

        // Bottoni per le opzioni
        loginButton = new Button("Accedi");
        registerButton = new Button("Registrati");
        essentialInfoButton = new Button("Prenota senza registrarti");

        // Aggiungi elementi alla root
        root.getChildren().addAll(title, description, loginButton, registerButton, essentialInfoButton);
    }

    public VBox getRoot() {
        return root;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getEssentialInfoButton() {
        return essentialInfoButton;
    }
}
