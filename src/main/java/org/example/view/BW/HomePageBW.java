package org.example.view.BW;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.view.HomePage;

public class HomePageBW extends HomePage {
    @Override
    protected void setStyle(Button bookRoom, Button bookActivity, Button login, Label title, Label description) {
        // Sfondo nero
        root.setStyle("-fx-background-color: #000000; -fx-padding: 20; -fx-alignment: center;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";
        String buttonHoverStyle = "-fx-background-color: #555555; -fx-text-fill: #FFFFFF;";

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

        // Stile del titolo e della descrizione
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18px; -fx-font-weight: bold;");
        description.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px;");
    }
}
