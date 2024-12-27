package org.example.view.colored;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.view.HomePage;

public class HomePageColored extends HomePage {
    @Override
    protected void setStyle(Button bookRoom, Button bookActivity, Button login, Label title, Label description) {
        // Sfondo caldo
        root.setStyle("-fx-background-color: #F4F1E1; -fx-padding: 20; -fx-alignment: center;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #2E3A8C; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 5;";
        String buttonHoverStyle = "-fx-background-color: #5F8FA6; -fx-text-fill: #FFFFFF;";

        setStyleDuplicateCode(bookRoom, bookActivity, login, buttonStyle, buttonHoverStyle);

        // Stile del titolo e della descrizione
        title.setStyle("-fx-text-fill: #000000; -fx-font-size: 18px; -fx-font-weight: bold;");
        description.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");
    }
}
