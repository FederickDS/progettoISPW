package org.example.view.control;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.example.control.BookRoom;

public interface NavigationService {
    void navigateToHomePage();
    void navigateToServiceSelection();
    void navigateToStartupSettings();
    void navigateToBookingRoom(BookRoom bookRoom);
    void display(Stage stage, VBox root, String title);
}