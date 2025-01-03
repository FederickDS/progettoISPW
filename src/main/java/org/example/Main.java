package org.example;

import org.example.view.control.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    NavigationManager navigationManager;
    @Override
    public void start(Stage stage) {
        navigationManager = new NavigationManager(stage);
        navigationManager.navigateToStartupSettings();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
