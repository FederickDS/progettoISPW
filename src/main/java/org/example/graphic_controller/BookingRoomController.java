package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.view.BookingRoom;

import java.util.logging.Logger;

public class BookingRoomController {
    private BookingRoom bookingRoom;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private BookRoom bookRoom;
    private final NavigationService navigationService;

    public BookingRoomController(NavigationService navigationService, BookRoom bookRoom) {
        this.navigationService = navigationService;
        this.bookRoom = bookRoom;
        bookingRoom = new BookingRoom();
        // Aggiungi gestione eventi per i bottoni
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone Conferma
        bookingRoom.getConfirmButton().setOnAction(e -> handleConfirm());

        // Bottone Annulla
        bookingRoom.getCancelButton().setOnAction(e -> handleCancel());
    }

    private void handleConfirm() {
        logger.info("Prenotazione confermata.");

        // Recupera le date selezionate (fare in bookRoom)
        var checkInDate = bookingRoom.getCheckInDate();
        var checkOutDate = bookingRoom.getCheckOutDate();

        if (checkInDate == null || checkOutDate == null) {
            logger.warning("Date non valide. Selezionare entrambe le date.");
            return;
        }

        if (checkInDate.isAfter(checkOutDate)) {
            logger.warning("La data di check-in deve precedere la data di check-out.");
        }
        // Salva la prenotazione (con bookRoom)
        if(!this.bookRoom.checkHoursAndSave(checkInDate,checkOutDate)){
            logger.warning("L'intervallo selezionato non è disponibile. Selezioni un nuovo intervallo");
        }
        // Passa alla pagina successiva
    }

    private void handleCancel() {
        logger.info("Prenotazione annullata. Torna alla Home Page.");
        navigationService.navigateToServiceSelection(this.navigationService);
    }

    public VBox getRoot(){
        return bookingRoom.getRoot();
    }
}
