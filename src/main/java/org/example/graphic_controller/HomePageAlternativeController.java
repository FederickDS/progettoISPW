package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.facade.ApplicationFacade;
import org.example.view.ClientHomePageAlternative;
import org.example.view.HomePageAlternative;
import org.example.view.ReceptionistHomePageAlternative;

public class HomePageAlternativeController {
    private Object homePageAlternative;
    private final NavigationService navigationService;
    private static final String HOME_PAGE = "HomePageAlternative";
    private static final String CLIENT = "client";
    private static final String RECEPTIONIST = "receptionist";
    private static final String ESSENTIAL_INFO = "essentialInfo";

    public HomePageAlternativeController(NavigationService navigationService) {
        this.navigationService = navigationService;
        determineHomePageAlternative();
    }

    private void determineHomePageAlternative() {
        String loginStatus = ApplicationFacade.checkLoginStatus();
        if (loginStatus == null || loginStatus.equalsIgnoreCase(ESSENTIAL_INFO)) {
            homePageAlternative = new HomePageAlternative();
        } else if (CLIENT.equalsIgnoreCase(loginStatus)) {
            homePageAlternative = new ClientHomePageAlternative();
        } else if (RECEPTIONIST.equalsIgnoreCase(loginStatus)) {
            homePageAlternative = new ReceptionistHomePageAlternative();
        }
        addEventHandlers(homePageAlternative);
    }

    private void addEventHandlers(Object homePageAlternative) {
        if (homePageAlternative instanceof HomePageAlternative hpa) {
            hpa.getConfirmButton().setOnAction(e -> handleConfirmSelection(hpa));
        } else if (homePageAlternative instanceof ClientHomePageAlternative chpa) {
            chpa.getConfirmButton().setOnAction(e -> handleConfirmSelection(chpa));
        } else if (homePageAlternative instanceof ReceptionistHomePageAlternative rhpa) {
            rhpa.getConfirmButton().setOnAction(e -> handleConfirmSelection(rhpa));
        }
    }

    private void handleConfirmSelection(HomePageAlternative page) {
        if (page.getSelectionGroup().getSelectedToggle() == null) {
            page.showSelectionError();
            return;
        }

        if (page.getBookRoomOption().isSelected()) {
            navigationService.navigateToRoomBookingOptionsAlternative(navigationService, HOME_PAGE, "ServiceSelectionAlternative");
        } else if (page.getBookActivityOption().isSelected()) {
            navigationService.navigateToNotImplemented(navigationService, HOME_PAGE);
        } else if (page.getLoginOption().isSelected()) {
            navigationService.navigateToLoginAlternative(navigationService, HOME_PAGE, HOME_PAGE, null);
        }
    }

    private void handleConfirmSelection(ClientHomePageAlternative page) {
        if (page.getSelectionGroup().getSelectedToggle() == null) {
            page.showSelectionError();
            return;
        }

        if (page.getBookRoomOption().isSelected()) {
            navigationService.navigateToServiceSelection(navigationService);
        } else if (page.getBookActivityOption().isSelected()) {
            navigationService.navigateToNotImplemented(navigationService, HOME_PAGE);
        } else if (page.getUserInfoOption().isSelected()) {
            navigationService.navigateToCustomerViewAlternative(navigationService);
        } else if (page.getLogoutOption().isSelected()) {
            goToOriginalHomePage();
        }
    }

    private void handleConfirmSelection(ReceptionistHomePageAlternative page) {
        if (page.getSelectionGroup().getSelectedToggle() == null) {
            page.showSelectionError();
            return;
        }

        if (page.getManageBookingsOption().isSelected()) {
            navigationService.navigateToReceptionistViewAlternative(navigationService);
        } else if (page.getManageActivitiesOption().isSelected()) {
            navigationService.navigateToNotImplemented(navigationService, HOME_PAGE);
        } else if (page.getLogoutOption().isSelected()) {
            goToOriginalHomePage();
        }
    }

    public void goToOriginalHomePage() {
        SessionManager.getInstance().clearSession();
        navigationService.navigateToHomePageAlternative(navigationService);
    }

    public VBox getRoot() {
        if (this.homePageAlternative instanceof HomePageAlternative hereHomePage) {
            return hereHomePage.getRoot();
        } else if (this.homePageAlternative instanceof ClientHomePageAlternative hereClientHomePage) {
            return hereClientHomePage.getRoot();
        } else if (this.homePageAlternative instanceof ReceptionistHomePageAlternative hereReceptionistHomePage) {
            return hereReceptionistHomePage.getRoot();
        }
        return null;
    }
}
