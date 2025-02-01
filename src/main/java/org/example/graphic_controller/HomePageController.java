package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.facade.ApplicationFacade;
import org.example.view.ClientHomePage;
import org.example.view.HomePage;
import org.example.view.ReceptionistHomePage;

public class HomePageController<T> {
    private T homePage;
    private final NavigationService navigationService;
    private static final String HOME_PAGE = "HomePage";
    private static final String CLIENT = "client";
    private static final String RECEPTIONIST = "receptionist";
    private static final String ESSENTIAL_INFO = "essentialInfo";

    public HomePageController(NavigationService navigationService) {
        this.navigationService = navigationService;
        determineHomePage();
    }

    private void determineHomePage() {
        String loginStatus = ApplicationFacade.checkLoginStatus();
        if (loginStatus == null || loginStatus.equalsIgnoreCase(ESSENTIAL_INFO)) { //credenziali errate o nulle
            homePage = (T) new HomePage();
        } else if (CLIENT.equalsIgnoreCase(loginStatus)) {
            homePage = (T) new ClientHomePage();
        } else if (RECEPTIONIST.equalsIgnoreCase(loginStatus)) {
            homePage = (T) new ReceptionistHomePage();
        }
        addEventHandlers(homePage);
    }

    private void addEventHandlers(Object homePage) {
        if (homePage instanceof HomePage hp) {
            hp.getBookRoomButton().setOnAction(e -> navigationService.navigateToRoomBookingOptions(navigationService, HOME_PAGE, "ServiceSelection"));
            hp.getloginButton().setOnAction(e -> navigationService.navigateToLogin(navigationService, HOME_PAGE, HOME_PAGE, CLIENT));
            hp.getBookActivityButton().setOnAction(e -> navigationService.navigateToNotImplemented(navigationService, HOME_PAGE));
            hp.getReceptionistAccessButton().setOnAction(e -> navigationService.navigateToLogin(navigationService, HOME_PAGE, HOME_PAGE, RECEPTIONIST));
        } else if (homePage instanceof ClientHomePage chp) {
            chp.getBookRoomButton().setOnAction(e -> navigationService.navigateToServiceSelection(navigationService));
            chp.getBookActivityButton().setOnAction(e -> navigationService.navigateToNotImplemented(navigationService,HOME_PAGE));
            chp.getUserInfoButton().setOnAction(e -> navigationService.navigateToCustomerView(navigationService));
            chp.getLogoutButton().setOnAction(e -> goToOriginalHomePage());
        } else if (homePage instanceof ReceptionistHomePage rhp) {
            rhp.getManageBookingsButton().setOnAction(e -> navigationService.navigateToReceptionistView(navigationService));
            rhp.getManageActivitiesButton().setOnAction(e -> navigationService.navigateToNotImplemented(navigationService,HOME_PAGE));
            rhp.getLogoutButton().setOnAction(e -> goToOriginalHomePage());
        }
    }

    public void goToOriginalHomePage() {
        SessionManager.getInstance().clearSession();
        navigationService.navigateToHomePage(navigationService);
    }

    public VBox getRoot(){
        if(this.homePage instanceof HomePage hereHomePage){
            return hereHomePage.getRoot();
        }else if(this.homePage instanceof ClientHomePage hereCLientHomePage){
            return hereCLientHomePage.getRoot();
        }else if(this.homePage instanceof ReceptionistHomePage hereReceptionistHomePage){
            return hereReceptionistHomePage.getRoot();
        }
        return null;
    }
}