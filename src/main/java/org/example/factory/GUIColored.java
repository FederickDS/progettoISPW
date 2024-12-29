package org.example.factory;

import org.example.view.BookingRoom;
import org.example.view.colored.BookingRoomColored;

public class GUIColored extends GUIFactory {
    @Override
    public BookingRoom createBookingView() {
        return new BookingRoomColored();
    }
}
