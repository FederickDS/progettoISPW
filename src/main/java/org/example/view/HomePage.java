package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.view.control.NavigationService;

import java.util.logging.Logger;

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

        // Bottoni per i casi d'uso
        bookRoomButton = new Button("Prenota Stanza");
        bookActivityButton = new Button("Prenota Attività");
        loginButton = new Button("Accedi");

        // Aggiungi i bottoni alla root
        root.getChildren().addAll(bookRoomButton, bookActivityButton, loginButton);

        // Titolo e descrizione
        Label title = new Label("Benvenuti al nostro Hotel di Mare!");
        Label description = new Label("Effettuate la vostra prenotazione e godetevi il soggiorno.");

        // Aggiungi titolo e descrizione alla root
        root.getChildren().addAll(title, description);
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

    // Metodo per mostrare la pagina
    public void display(Stage stage, NavigationService navigationService) {
        navigationService.display(stage, root,"Home Page");
    }
}
