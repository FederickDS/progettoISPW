package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.RoomBookingOptionsView;

public class RoomBookingOptionsController {
    private final RoomBookingOptionsView view;
    private final NavigationService navigationService;
    private final String previousPage;
    private final String nextPage;
    private static final String CLIENT = "Client";

    public RoomBookingOptionsController(NavigationService navigationService, String previousPage, String nextPage) {
        this.navigationService = navigationService;
        this.view = new RoomBookingOptionsView();
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Gestione eventi per i bottoni
        view.getLoginButton().setOnAction(e -> navigateToLogin());
        view.getRegisterButton().setOnAction(e -> navigateToRegistration());
        view.getEssentialInfoButton().setOnAction(e -> navigateToEssentialInfo());
        view.getBackButton().setOnAction(e -> navigateBack());
    }

    private void navigateToLogin() {
        navigationService.navigateToLogin(navigationService, previousPage, nextPage, CLIENT);
    }

    private void navigateToRegistration() {
        navigationService.navigateToRegistration(navigationService, previousPage, nextPage, CLIENT);
    }

    private void navigateToEssentialInfo() {
        navigationService.navigateToEssentialInfo(navigationService, previousPage, nextPage);
    }

    private void navigateBack() {
        navigationService.navigateBack(previousPage, navigationService);
    }

    public VBox getRoot() {
        return view.getRoot();
    }
}