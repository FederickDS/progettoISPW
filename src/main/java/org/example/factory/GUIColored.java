package org.example.factory;

import org.example.view.BookingRoom;
import org.example.view.colored.BookingRoomColored;
import org.example.view.colored.HomePageColored;
import org.example.view.HomePage;

import org.example.view.ServiceSelection;
import org.example.view.colored.ServiceSelectionColored;

public class GUIColored extends GUIFactory {
    @Override
    public HomePage createHomePage() {
        return new HomePageColored();
    }
    @Override
    public ServiceSelection createServiceSelection() {
        return new ServiceSelectionColored();
    }
    @Override
    public BookingRoom createBookingView() {
        return new BookingRoomColored();
    }
}
