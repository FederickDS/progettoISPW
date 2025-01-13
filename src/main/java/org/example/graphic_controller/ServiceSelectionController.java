package org.example.graphic_controller;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.entity.Client;
import org.example.entity.User;
import org.example.view.ServiceSelection;
import org.example.entity.Activity;
import org.example.entity.Service;

import java.util.logging.Logger;
import java.util.List;

public class ServiceSelectionController {
    private ServiceSelection serviceSelection;
    private final NavigationService navigationService;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final User user;
    private final BookRoom bookRoom;
    //aggiunta servizi dinamici

    public ServiceSelectionController(NavigationService navigationService, User newUser) {
        this.navigationService = navigationService;
        this.user = newUser;
        // Determina quale view caricare in base alle impostazioni
        this.serviceSelection = new ServiceSelection();
        //crea bookRoom (inizia caso d'uso)
        this.bookRoom = new BookRoom();
        bookRoom.addFirstClient((Client) newUser);
        // Popola la View con attività e servizi
        loadAvailableActivitiesAndServices();
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
        // Salvataggio utente quando creiamo caso d'uso
        BookRoom bookRoom = new BookRoom();
        bookRoom.addFirstClient((Client) user);//minori controlli
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

    private void loadAvailableActivitiesAndServices() {
        // Carica le attività
        List<Activity> activities = bookRoom.getAvailableActivities();
        for (Activity activity : activities) {
            CheckBox activityCheckBox = new CheckBox(activity.getName());
            serviceSelection.getActivitySection().getChildren().add(activityCheckBox);
        }

        // Carica i servizi
        List<Service> services = bookRoom.getAvailableServices();
        for (Service service : services) {
            CheckBox serviceCheckBox = new CheckBox(service.getName());
            serviceSelection.getServiceSection().getChildren().add(serviceCheckBox);
        }
    }

}
