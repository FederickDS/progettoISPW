package org.example.view.colored;

import org.example.view.ServiceSelection;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServiceSelectionColored extends ServiceSelection {

    @Override
    protected void setStyle(Label serviceLabel, Label activityLabel, Button confirmButton, Button cancelButton) {
        // Sfondo caldo
        root.setStyle("-fx-background-color: #F4F1E1; -fx-padding: 20; -fx-alignment: center;");

        // Stile dei titoli
        serviceLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 16px; -fx-font-weight: bold;");
        activityLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 16px; -fx-font-weight: bold;");

        // Stile dei bottoni
        String buttonStyle = "-fx-background-color: #2E3A8C; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;";
        String buttonHoverStyle = "-fx-background-color: #5F8FA6; -fx-text-fill: #FFFFFF;";

        setStyleDuplicate(buttonStyle, buttonHoverStyle, confirmButton, cancelButton);
    }
}

