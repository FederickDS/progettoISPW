package org.example.view.bw;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.view.BookingRoom;

public class BookingRoomBW extends BookingRoom {
    @Override
    protected void setStyle(Label title, Label checkInLabel, Label checkOutLabel, Button confirmButton, Button cancelButton) {
        // Sfondo bianco
        root.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 20; -fx-alignment: center;");

        // Stile del titolo e delle etichette
        title.setStyle("-fx-text-fill: #000000; -fx-font-size: 18px; -fx-font-weight: bold;");
        checkInLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");
        checkOutLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";
        String buttonHoverStyle = "-fx-background-color: #555555; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";

        setStyleDuplicateCode(confirmButton, cancelButton, buttonStyle, buttonHoverStyle);
    }
}
