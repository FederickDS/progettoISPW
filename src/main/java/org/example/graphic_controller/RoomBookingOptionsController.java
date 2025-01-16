package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.RoomBookingOptionsView;

public class RoomBookingOptionsController {
    private final RoomBookingOptionsView view;
    private final NavigationService navigationService;
    private final String ROOM_BOOKING_OPTIONS = "RoomBookingOptions";
    private final String SERVICE_SELECTION = "ServiceSelection";
    private final String CLIENT = "Client";

    public RoomBookingOptionsController(NavigationService navigationService) {
        this.navigationService = navigationService;
        this.view = new RoomBookingOptionsView();
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Gestione eventi per i bottoni
        view.getLoginButton().setOnAction(e -> navigateToLogin());
        view.getRegisterButton().setOnAction(e -> navigateToRegistration());
        view.getEssentialInfoButton().setOnAction(e -> navigateToEssentialInfo());
    }

    private void navigateToLogin() {
        navigationService.navigateToLogin(navigationService, ROOM_BOOKING_OPTIONS, SERVICE_SELECTION, CLIENT);
    }

    private void navigateToRegistration() {
        navigationService.navigateToRegistration(navigationService, ROOM_BOOKING_OPTIONS, SERVICE_SELECTION, CLIENT);
    }

    private void navigateToEssentialInfo() {
        navigationService.navigateToEssentialInfo(navigationService, ROOM_BOOKING_OPTIONS, SERVICE_SELECTION);
    }

    public VBox getView() {
        return view.getRoot();
    }
}
