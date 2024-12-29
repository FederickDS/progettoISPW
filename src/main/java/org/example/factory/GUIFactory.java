package org.example.factory;

import org.example.view.BookingRoom;
import org.example.view.HomePage;
import org.example.view.ServiceSelection;

public abstract class GUIFactory {
    // Metodo per creare la schermata di selezione servizi inclusi nel soggiorno
    public abstract ServiceSelection createServiceSelection();
    //Metodo per creare la schermata per selezionare data e numero di persone
    public abstract BookingRoom createBookingView();
}
