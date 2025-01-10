package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.view.ServiceSelection;

import java.util.logging.Logger;

public class ServiceSelectionController {
    private ServiceSelection serviceSelection;
    private final NavigationService navigationService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public ServiceSelectionController(NavigationService navigationService) {
        this.navigationService = navigationService;
        // Determina quale view caricare in base alle impostazioni
        serviceSelection = new ServiceSelection();
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
        navigationService.navigateToBookingRoom(this.navigationService, bookRoom);
    }

    public void handleCancel() {
        logger.info("Scelte annullate. Torna alla Home Page.");
        navigationService.navigateToHomePage(this.navigationService);
    }

    public VBox getView(){
        return serviceSelection.getRoot();
    }

}
