package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.RoomBookingOptionsView;

public class RoomBookingOptionsController {
    private final RoomBookingOptionsView view;
    private final NavigationService navigationService;

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
        navigationService.navigateToLogin(navigationService, "RoomBookingOptions", "ServiceSelection", "client");
    }

    private void navigateToRegistration() {
        navigationService.navigateToRegistration(navigationService, "RoomBookingOptions", "ServiceSelection", "client");
    }

    private void navigateToEssentialInfo() {
        //navigationService.navigateToEssentialInfo(navigationService, "RoomBookingOptions", "RoomBooking");
    }

    public VBox getView() {
        return view.getRoot();
    }
}
