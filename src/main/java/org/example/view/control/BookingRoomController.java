package org.example.view.control;

import org.example.entity.StartupSettingsEntity;
import org.example.factory.GUIFactory;
import org.example.view.BookingRoom;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class BookingRoomController {
    private BookingRoom bookingRoom;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Stage stage;
    private final NavigationService navigationService;

    public BookingRoomController(Stage stage, NavigationService navigationService) {
        this.stage = stage;
        this.navigationService = navigationService;
    }

    public void loadBookingView() {
        // Determina quale view caricare in base alle impostazioni
        GUIFactory factory = StartupSettingsEntity.getInstance().typeOfGUI();

        bookingRoom = factory.createBookingView();
        // Inizializza la view
        initController(this.stage);
    }

    private void initController(Stage stage) {
        // Mostra la view
        bookingRoom.display(stage);

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

        // Recupera le date selezionate
        var checkInDate = bookingRoom.getCheckInDate();
        var checkOutDate = bookingRoom.getCheckOutDate();

        if (checkInDate == null || checkOutDate == null) {
            logger.warning("Date non valide. Selezionare entrambe le date.");
            return;
        }

        if (checkInDate.isAfter(checkOutDate)) {
            logger.warning("La data di check-in deve precedere la data di check-out.");
            return;
        }

        // Salva la prenotazione (con bookRoom)

        // Passa alla pagina successiva
    }

    private void handleCancel() {
        logger.info("Prenotazione annullata. Torna alla Home Page.");
        navigationService.navigateToServiceSelection();
    }
}
