package org.example.view.control;

import org.example.control.BookRoom;

public interface NavigationService {
    void navigateToHomePage();
    void navigateToServiceSelection();
    void navigateToStartupSettings();
    void navigateToBookingRoom(BookRoom bookRoom);
}