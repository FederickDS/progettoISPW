package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.BookingRoomBean;
import org.example.facade.ApplicationFacade;
import org.example.facade.ApplicationFacadeInterface;
import org.example.factory.ModelBeanFactory;
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
        ApplicationFacade applicationFacade = new ApplicationFacade();

        BookingRoomBean bookingRoomBean = ModelBeanFactory.setBookingRoomBean(bookingRoom, applicationFacade);

        if(!bookingRoomBean.checkCompatibleData(bookingRoom, applicationFacade)) {
            return;
        }

        // Salva la prenotazione (con bookRoom)
        if(!this.bookRoom.checkHours(bookingRoomBean)){
            logger.info("ci entra?");
            bookingRoom.setCheckInError("L'intervallo selezionato include giorni in cui l'hotel è chiuso.");
            return;
        }
        //trova la stanza
        int roomNumber = this.bookRoom.selectRoom(bookingRoomBean);
        if(roomNumber==-1){
            bookingRoom.setCheckInError("L'intervallo selezionato è occupato da altre stanze.");
            return;
        }
        bookingRoomBean.setRoomNumber(roomNumber);
        //numero di stanza valido, salviamo tutto
        bookRoom.saveReservation(bookingRoomBean);

        //dovrei controllare database e cambiare pagina, metto quella fake
        navigateToNextPage();
    }

    private void handleCancel() {
        logger.info("Prenotazione annullata. Torna alla Home Page.");
        navigationService.navigateToServiceSelection(this.navigationService);
    }

    private void navigateToNextPage() {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
        navigationService.navigateToReservationPayment(this.navigationService,this.bookRoom);
    }

    public VBox getRoot(){
        return bookingRoom.getRoot();
    }
}
