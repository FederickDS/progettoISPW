package org.example;

import org.example.graphic_controller.NavigationManager;
import org.example.graphic_controller.NavigationManagerAlternative;
import org.example.graphic_controller.NavigationService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private NavigationService navigationService;

    @Override
    public void start(Stage stage) {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton mainInterfaceButton = new RadioButton("Interfaccia Principale");
        mainInterfaceButton.setToggleGroup(toggleGroup);
        mainInterfaceButton.setSelected(true);

        RadioButton secondaryInterfaceButton = new RadioButton("Interfaccia Secondaria");
        secondaryInterfaceButton.setToggleGroup(toggleGroup);

        Button confirmButton = new Button("Conferma");
        confirmButton.setOnAction(e -> {
            if (mainInterfaceButton.isSelected()) {
                navigationService = new NavigationManager(stage);
            } else {
                navigationService = new NavigationManagerAlternative(stage);
            }
            navigationService.navigateToStartupSettings(navigationService);
        });

        VBox root = new VBox(10, mainInterfaceButton, secondaryInterfaceButton, confirmButton);
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Seleziona Interfaccia");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
