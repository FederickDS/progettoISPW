package org.example.factory;

import org.example.view.BookingRoom;
import org.example.view.bw.BookingRoomBW;
import org.example.view.bw.ServiceSelectionBW;
import org.example.view.ServiceSelection;

public class GUIBw extends GUIFactory {
    @Override
    public ServiceSelection createServiceSelection() {
        return new ServiceSelectionBW();
    }
    @Override
    public BookingRoom createBookingView() {
        return new BookingRoomBW();
    }
}
