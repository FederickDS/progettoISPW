package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.HomePage;

public class HomePageController {
    private final HomePage homePage;
    private final NavigationService navigationService;
    private static final String HOME_PAGE = "HomePage";

    public HomePageController(NavigationService navigationService) {
        this.navigationService = navigationService;
        // Crea l'HomePage tramite la factory
        this.homePage = new HomePage();

        addEventHandlers();
    }

    private void addEventHandlers() {
        // Gestione evento "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationService.navigateToRoomBookingOptions(navigationService, HOME_PAGE, "ServiceSelection"));

        // Effettua l'accesso (per area riservata)
        homePage.getloginButton().setOnAction(e -> navigationService.navigateToLogin(navigationService, HOME_PAGE, HOME_PAGE, "Client"));

        //gestione altri bottoni
        homePage.getbookActivityButton().setOnAction(e->navigationService.navigateToNotImplemented(navigationService, HOME_PAGE));
    }

    public VBox getView(){
        return homePage.getRoot();
    }
}