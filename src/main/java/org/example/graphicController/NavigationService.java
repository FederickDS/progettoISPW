package org.example.graphicController;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.example.appController.BookRoom;

public interface NavigationService {
    void navigateToHomePage();
    void navigateToServiceSelection();
    void navigateToStartupSettings();
    void navigateToBookingRoom(BookRoom bookRoom);
    void navigateToLogin(String previousPage, String nextPage, String type);
    void navigateToRegistration(String previousPage, String nextPage, String userType);
    void display(Stage stage, VBox root, String title);
}