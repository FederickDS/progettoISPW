package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ClientHomePage {
    private final VBox root;
    private final Button bookRoomButton;
    private final Button bookActivityButton;
    private final Button userInfoButton;
    private final Button logoutButton;

    public ClientHomePage() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        bookRoomButton = new Button("Prenota Stanza");
        bookActivityButton = new Button("Prenota Attività");
        userInfoButton = new Button("Le Tue Info");
        logoutButton = new Button("Logout");

        root.getChildren().addAll(bookRoomButton, bookActivityButton, userInfoButton, logoutButton);
    }

    public VBox getRoot() {
        return root;
    }

    public Button getBookRoomButton() {
        return bookRoomButton;
    }

    public Button getBookActivityButton() {
        return bookActivityButton;
    }

    public Button getUserInfoButton() {
        return userInfoButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}