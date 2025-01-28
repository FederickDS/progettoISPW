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
    private static final String PAGE_REFERENCE = "BookingRoomController";

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
        var numberOfParticipants = bookingRoom.getParticipants();

        if(!bookRoom.checkCompatibleData(bookingRoom)){
            return;
        }

        // Salva la prenotazione (con bookRoom)
        if(!this.bookRoom.checkHours(checkInDate,checkOutDate)){
            logger.info("ci entra?");
            bookingRoom.setCheckInError("L'intervallo selezionato include giorni in cui l'hotel è chiuso.");
            return;
        }
        //trova la stanza
        int roomNumber = this.bookRoom.selectRoom(checkInDate,checkOutDate,numberOfParticipants);
        if(roomNumber==-1){
            bookingRoom.setCheckInError("L'intervallo selezionato è occupato da altre stanze.");
            return;
        }
        //numero di stanza valido, salviamo tutto
        bookRoom.saveReservation(roomNumber, checkInDate, checkOutDate);

        //dovrei controllare database e cambiare pagina, metto quella fake
        navigateToNextPage();
    }

    private void handleCancel() {
        logger.info("Prenotazione annullata. Torna alla Home Page.");
        navigationService.navigateToServiceSelection(this.navigationService);
    }

    private void navigateToNextPage() {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
        navigationService.navigateToNotImplemented(this.navigationService,PAGE_REFERENCE);
    }

    public VBox getRoot(){
        return bookingRoom.getRoot();
    }
}
