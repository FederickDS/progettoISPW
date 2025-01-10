package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;

public interface NavigationService {
    void navigateToHomePage();
    void navigateToServiceSelection();
    void navigateToStartupSettings();
    void navigateToBookingRoom(BookRoom bookRoom);
    void navigateToLogin(String previousPage, String nextPage, String type);
    void navigateToRegistration(String previousPage, String nextPage, String userType);
    void display(VBox root, String title);
    String hashWithSHA256(String input);
    }