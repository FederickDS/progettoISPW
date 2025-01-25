package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NotImplementedView {
    private final VBox root;
    private final Button backButton;

    public NotImplementedView() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label message = new Label("Questa pagina non è ancora stata implementata.");
        backButton = new Button("Indietro");

        root.getChildren().addAll(message, backButton);
    }

    public Button getBackButton() {
        return backButton;
    }

    public VBox getRoot() {
        return root;
    }
}