package org.example.factory;

import org.example.view.BookingRoom;
import org.example.view.bw.BookingRoomBW;
import org.example.view.bw.ServiceSelectionBW;
import org.example.view.ServiceSelection;
import org.example.view.bw.HomePageBW;
import org.example.view.HomePage;

public class GUIBw extends GUIFactory {
    @Override
    public HomePage createHomePage() {
        return new HomePageBW();
    }
    @Override
    public ServiceSelection createServiceSelection() {
        return new ServiceSelectionBW();
    }
    @Override
    public BookingRoom createBookingView() {
        return new BookingRoomBW();
    }
}
