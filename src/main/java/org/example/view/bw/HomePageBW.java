package org.example.view.bw;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.view.HomePage;

public class HomePageBW extends HomePage {
    @Override
    protected void setStyle(Button bookRoom, Button bookActivity, Button login, Label title, Label description) {
        // Sfondo nero
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 20; -fx-alignment: center;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";
        String buttonHoverStyle = "-fx-background-color: #555555; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";

        setStyleDuplicateCode(bookRoom, bookActivity, login, buttonStyle, buttonHoverStyle);

        // Stile del titolo e della descrizione
        title.setStyle("-fx-text-fill: #000000; -fx-font-size: 18px; -fx-font-weight: bold;");
        description.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");
    }
}
