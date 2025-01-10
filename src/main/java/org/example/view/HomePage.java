package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomePage {
    protected VBox root;
    private final Button bookRoomButton;
    private final Button bookActivityButton;
    private final Button loginButton;


    public HomePage() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Titolo e descrizione
        Label title = new Label("Benvenuti al nostro Hotel di Mare!");
        Label description = new Label("Effettuate la vostra prenotazione e godetevi il soggiorno.");

        // Bottoni per i casi d'uso
        bookRoomButton = new Button("Prenota Stanza");
        bookActivityButton = new Button("Prenota Attività");
        loginButton = new Button("Accedi");

        // Aggiungi alla root
        root.getChildren().addAll(title, description, bookRoomButton, bookActivityButton, loginButton);
    }

    public Button getBookRoomButton() {
        return bookRoomButton;
    }
    public Button getbookActivityButton() {
        return bookActivityButton;
    }
    public Button getloginButton() {
        return loginButton;
    }
    public VBox getRoot() {return root;}
}
