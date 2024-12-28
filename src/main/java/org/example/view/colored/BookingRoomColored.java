package org.example.view.colored;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.view.BookingRoom;

public class BookingRoomColored extends BookingRoom {
    @Override
    protected void setStyle(Label title, Label checkInLabel, Label checkOutLabel, Button confirmButton, Button cancelButton) {
        // Sfondo caldo
        root.setStyle("-fx-background-color: #F4F1E1; -fx-padding: 20; -fx-alignment: center;");

        // Stile del titolo e delle etichette
        title.setStyle("-fx-text-fill: #000000; -fx-font-size: 18px; -fx-font-weight: bold;");
        checkInLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");
        checkOutLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #2E3A8C; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 5;";
        String buttonHoverStyle = "-fx-background-color: #5F8FA6; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 5;";

        setStyleDuplicateCode(confirmButton, cancelButton, buttonStyle, buttonHoverStyle);
    }
}
