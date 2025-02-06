package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import org.example.view.RoomBookingOptionsViewAlternative;

public class RoomBookingOptionsControllerAlternative {
    private final RoomBookingOptionsViewAlternative view;
    private final NavigationService navigationService;
    private final String previousPage;
    private final String nextPage;
    private static final String CLIENT = "client";

    public RoomBookingOptionsControllerAlternative(NavigationService navigationService, String previousPage, String nextPage) {
        this.navigationService = navigationService;
        this.view = new RoomBookingOptionsViewAlternative();
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        addEventHandlers();
    }

    private void addEventHandlers() {
        ToggleGroup toggleGroup = view.getToggleGroup();
        view.getConfirmButton().setOnAction(e -> handleSelection(toggleGroup));
        view.getBackButton().setOnAction(e -> navigateBack());
    }

    private void handleSelection(ToggleGroup toggleGroup) {
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
        if (selected != null) {
            String selection = selected.getText();
            switch (selection) {
                case "Accedi":
                    navigateToLogin();
                    break;
                case "Registrati":
                    navigateToRegistration();
                    break;
                case "Prenota senza registrarti":
                    navigateToEssentialInfo();
                    break;
            }
        }
    }

    private void navigateToLogin() {
        navigationService.navigateToLoginAlternative(navigationService, previousPage, nextPage, CLIENT);
    }

    private void navigateToRegistration() {
        navigationService.navigateToRegistrationAlternative(navigationService, previousPage, nextPage, CLIENT);
    }

    private void navigateToEssentialInfo() {
        navigationService.navigateToEssentialInfoAlternative(navigationService, previousPage, nextPage);
    }

    private void navigateBack() {
        navigationService.navigateBack(previousPage, navigationService);
    }

    public VBox getRoot() {
        return view.getRoot();
    }
}