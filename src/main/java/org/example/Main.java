package org.example;

import org.example.control.StartupSettingsController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        new StartupSettingsController(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
