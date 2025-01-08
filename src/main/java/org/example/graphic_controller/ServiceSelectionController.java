package org.example.graphic_controller;

import org.example.app_controller.BookRoom;
import org.example.view.ServiceSelection;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class ServiceSelectionController {
    private ServiceSelection serviceSelection;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Stage stage;
    private final NavigationService navigationService;

    public ServiceSelectionController(Stage stage, NavigationService navigationService) {
        this.stage = stage;
        this.navigationService = navigationService;
    }

    public void loadServiceSelection() {
        // Determina quale view caricare in base alle impostazioni
        serviceSelection = new ServiceSelection();
        // Inizializza la view
        initController(this.stage);
    }

    private void initController(Stage stage) {
        // Mostra la view
        navigationService.display(stage, serviceSelection.getVBox(),"Selezione Servizi e Attività");
        // Aggiungi gestione eventi per i bottoni
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone Conferma
        serviceSelection.getConfirmButton().setOnAction(e -> handleConfirm());

        // Bottone Annulla
        serviceSelection.getCancelButton().setOnAction(e -> handleCancel());
    }

    public void handleConfirm() {
        logger.info("Scelte confermate. Procedi con le azioni successive.");
        // Aggiungi logica per salvare le scelte
        BookRoom bookRoom = new BookRoom();
        //metodo per registrare attivita da salvare
        bookRoom.setServicesToReservation();
        //pagina successiva
        navigationService.navigateToBookingRoom(bookRoom);
    }

    public void handleCancel() {
        logger.info("Scelte annullate. Torna alla Home Page.");
        navigationService.navigateToHomePage();
    }
}
