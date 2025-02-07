package org.example.graphic_controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.entity.Client;
import org.example.facade.ApplicationFacade;
import org.example.view.ServiceSelectionAlternative;
import org.example.entity.Activity;
import org.example.entity.Service;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

public class ServiceSelectionControllerAlternative {
    private ServiceSelectionAlternative serviceSelection;
    private List<Activity> selectedActivities;
    private List<Service> selectedServices;
    private final NavigationService navigationService;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final BookRoom bookRoom;

    public ServiceSelectionControllerAlternative(NavigationService navigationService) {
        this.navigationService = navigationService;
        this.serviceSelection = new ServiceSelectionAlternative();
        this.bookRoom = new BookRoom();
        this.bookRoom.addFirstClient((Client) ApplicationFacade.getUserFromLogin());
        this.selectedActivities = new ArrayList<>();
        this.selectedServices = new ArrayList<>();
        loadAvailableActivitiesAndServices();
        addEventHandlers();
    }

    private void addEventHandlers() {
        serviceSelection.getConfirmButton().setOnAction(e -> handleConfirm());
        serviceSelection.getCancelButton().setOnAction(e -> handleCancel());
        serviceSelection.getAddActivityButton().setOnAction(e -> handleAddActivity());
    }

    public void confirmSelection() {
        bookRoom.setServicesToReservation(selectedActivities, selectedServices);
    }

    public void handleConfirm() {
        confirmSelection();
        logger.info("Scelte confermate. Procedi con le azioni successive.");
        navigationService.navigateToBookingRoom(navigationService, bookRoom);
    }

    public void handleCancel() {
        logger.info("Scelte annullate. Torna alla Home Page.");
        navigationService.navigateToHomePage(navigationService);
    }

    public void addActivityByName(String activityName) {
        for (Activity activity : bookRoom.getAvailableActivities()) {
            if (activity.getName().equals(activityName) && !selectedActivities.contains(activity)) {
                selectedActivities.add(activity);
                break;
            }
        }
    }

    public void handleAddActivity() {
        String selectedActivity = serviceSelection.getActivityDropdown().getValue();
        if (selectedActivity != null && !selectedActivity.isEmpty()) {
            addActivityByName(selectedActivity);
            serviceSelection.getActivityDropdown().getItems().remove(selectedActivity);
        }
    }

    public VBox getRoot(){
        return serviceSelection.getRoot();
    }

    private void loadAvailableActivitiesAndServices() {
        List<Activity> activities = bookRoom.getAvailableActivities();
        ComboBox<String> activityDropdown = serviceSelection.getActivityDropdown();
        for (Activity activity : activities) {
            activityDropdown.getItems().add(activity.getName());
        }

        List<Service> services = bookRoom.getAvailableServices();
        for (Service service : services) {
            serviceSelection.getServiceSection().getChildren().add(new Label(service.getName()));
            selectedServices.add(service);
        }
    }
}
