package org.example.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class HomePage {
    protected VBox root;
    private final Button bookRoomButton;
    private final Button bookActivityButton;
    private final Button loginButton;


    protected HomePage() {
        // Inizializzazione layout
        root = new VBox(20);
        root.setPrefSize(600, 400);
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

        // Modifica i colori tramite il metodo astratto
        setStyle(bookRoomButton, bookActivityButton, loginButton, title, description);
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

    // Metodo astratto per impostare lo stile specifico
    protected abstract void setStyle(Button bookRoom, Button bookActivity, Button login, Label title, Label description);

    protected void setStyleDuplicateCode(Button bookRoom, Button bookActivity, Button login, String buttonStyle, String buttonHoverStyle){
        bookRoom.setStyle(buttonStyle);
        bookActivity.setStyle(buttonStyle);
        login.setStyle(buttonStyle);

        // Cambia colore al passaggio del mouse
        bookRoom.setOnMouseEntered(e -> bookRoom.setStyle(buttonHoverStyle));
        bookRoom.setOnMouseExited(e -> bookRoom.setStyle(buttonStyle));

        bookActivity.setOnMouseEntered(e -> bookActivity.setStyle(buttonHoverStyle));
        bookActivity.setOnMouseExited(e -> bookActivity.setStyle(buttonStyle));

        login.setOnMouseEntered(e -> login.setStyle(buttonHoverStyle));
        login.setOnMouseExited(e -> login.setStyle(buttonStyle));

    }
    // Metodo per mostrare la pagina
    public void display(Stage stage) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }
}
