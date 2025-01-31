package org.example;

import org.example.graphic_controller.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.graphic_controller.NavigationService;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        NavigationService navigationService = new NavigationManager(stage);
        navigationService.navigateToStartupSettings(navigationService);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
