package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.BookingRoomBean;
import org.example.facade.ApplicationFacade;
import org.example.factory.ModelBeanFactory;
import org.example.view.BookingRoomAlternative;

import java.util.logging.Logger;

public class BookingRoomControllerAlternative {
    private BookingRoomAlternative bookingRoom;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private BookRoom bookRoom;
    private final NavigationService navigationService;

    public BookingRoomControllerAlternative(NavigationService navigationService, BookRoom bookRoom) {
        this.navigationService = navigationService;
        this.bookRoom = bookRoom;
        bookingRoom = new BookingRoomAlternative();
        addEventHandlers();
    }

    private void addEventHandlers() {
        bookingRoom.getConfirmButton().setOnAction(e -> handleConfirm());
        bookingRoom.getCancelButton().setOnAction(e -> handleCancel());
    }

    private void handleConfirm() {
        logger.info("Prenotazione confermata.");
        ApplicationFacade applicationFacade = new ApplicationFacade();

        BookingRoomBean bookingRoomBean = ModelBeanFactory.setBookingRoomBean(bookingRoom, applicationFacade);

        if (!bookingRoomBean.checkCompatibleData(bookingRoom, applicationFacade)) {
            return;
        }

        if (!this.bookRoom.checkHours(bookingRoomBean)) {
            bookingRoom.setCheckInError("L'intervallo selezionato include giorni in cui l'hotel è chiuso.");
            return;
        }

        int roomNumber = this.bookRoom.selectRoom(bookingRoomBean);
        if (roomNumber == -1) {
            bookingRoom.setCheckInError("L'intervallo selezionato è occupato da altre stanze.");
            return;
        }

        bookingRoomBean.setRoomNumber(roomNumber);
        bookRoom.saveReservation(bookingRoomBean);

        navigateToNextPage();
    }

    private void handleCancel() {
        logger.info("Prenotazione annullata. Torna alla Home Page.");
        navigationService.navigateToServiceSelectionAlternative(this.navigationService);
    }

    private void navigateToNextPage() {
        navigationService.navigateToReservationPaymentAlternative(this.navigationService, this.bookRoom);
    }

    public VBox getRoot() {
        return bookingRoom.getRoot();
    }
}
