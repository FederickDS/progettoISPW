package org.example.graphic_controller;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.application_controller.ValidateLogin;
import org.example.entity.Client;
import org.example.view.ServiceSelection;
import org.example.entity.Activity;
import org.example.entity.Service;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

public class ServiceSelectionController {
    private ServiceSelection serviceSelection;
    private final NavigationService navigationService;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final BookRoom bookRoom;
    //aggiunta servizi dinamici

    public ServiceSelectionController(NavigationService navigationService) {
        this.navigationService = navigationService;
        // Determina quale view caricare in base alle impostazioni
        this.serviceSelection = new ServiceSelection();
        //crea bookRoom (inizia caso d'uso)
        this.bookRoom = new BookRoom();
        ValidateLogin login = new ValidateLogin();
        this.bookRoom.addFirstClient((Client) login.validate(SessionManager.getInstance().getEmail(),SessionManager.getInstance().getPassword(),SessionManager.getInstance().getType()));
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
        List<Activity> selectedActivities = new ArrayList<>();
        List<Service> selectedServices = new ArrayList<>();

        // Itera sui nodi, eseguendo un cast sicuro a CheckBox
        for (Node node : serviceSelection.getActivitySection().getChildren()) {
            if (node instanceof CheckBox activityCheckBox && activityCheckBox.isSelected()) {
                for (Activity activity : this.bookRoom.getAvailableActivities()) {
                    if (activity.getName().equals(activityCheckBox.getText())) {
                        selectedActivities.add(activity);
                        logger.info(activity.getName());
                        break; // Evita duplicati
                    }
                }
            }
        }

        for (Node node : serviceSelection.getServiceSection().getChildren()) {
            if (node instanceof CheckBox serviceCheckBox && serviceCheckBox.isSelected()) {
                for (Service service : this.bookRoom.getAvailableServices()) {
                    if (service.getName().equals(serviceCheckBox.getText())) {
                        selectedServices.add(service);
                        logger.info(service.getName());
                        break; // Evita duplicati
                    }
                }
            }
        }

        //metodo per registrare attivita da salvare
        this.bookRoom.setServicesToReservation(selectedActivities, selectedServices);
        //pagina successiva
        navigationService.navigateToBookingRoom(this.navigationService, this.bookRoom);
    }

    public void handleCancel() {
        logger.info("Scelte annullate. Torna alla Home Page.");
        navigationService.navigateToHomePage(this.navigationService);
    }

    public VBox getRoot(){
        return serviceSelection.getRoot();
    }

    private void loadAvailableActivitiesAndServices() {
        // Carica le attività
        List<Activity> activities = this.bookRoom.getAvailableActivities();
        for (Activity activity : activities) {
            CheckBox activityCheckBox = new CheckBox(activity.getName());
            serviceSelection.getActivitySection().getChildren().add(activityCheckBox);
        }

        // Carica i servizi
        List<Service> services = this.bookRoom.getAvailableServices();
        for (Service service : services) {
            CheckBox serviceCheckBox = new CheckBox(service.getName());
            serviceSelection.getServiceSection().getChildren().add(serviceCheckBox);
        }
    }

}
