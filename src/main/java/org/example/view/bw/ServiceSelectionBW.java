package org.example.view.bw;

import org.example.view.ServiceSelection;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServiceSelectionBW extends ServiceSelection {

    @Override
    protected void setStyle(Label serviceLabel, Label activityLabel, Button confirmButton, Button cancelButton) {
        // Sfondo nero
        root.setStyle("-fx-background-color: #000000; -fx-padding: 20; -fx-alignment: center;");

        // Stile dei titoli
        serviceLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: bold;");
        activityLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: bold;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";
        String buttonHoverStyle = "-fx-background-color: #555555; -fx-text-fill: #FFFFFF;";

        setStyleDuplicate(buttonStyle, buttonHoverStyle, confirmButton, cancelButton);
    }
}
