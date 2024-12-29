package org.example.factory;

import org.example.view.BookingRoom;
import org.example.view.bw.BookingRoomBW;

public class GUIBw extends GUIFactory {
    @Override
    public BookingRoom createBookingView() {
        return new BookingRoomBW();
    }
}
