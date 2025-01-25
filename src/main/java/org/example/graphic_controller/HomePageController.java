package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.HomePage;

public class HomePageController {
    private final HomePage homePage;
    private final NavigationService navigationService;

    public HomePageController(NavigationService navigationService) {
        this.navigationService = navigationService;
        // Crea l'HomePage tramite la factory
        this.homePage = new HomePage();

        addEventHandlers();
    }

    private void addEventHandlers() {
        // Gestione evento "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationService.navigateToRoomBookingOptions(navigationService, "HomePage", "ServiceSelection"));

        // Effettua l'accesso (per area riservata)
        homePage.getloginButton().setOnAction(e -> navigationService.navigateToLogin(navigationService, "HomePage", "HomePage", "Client"));

        //gestione altri bottoni
        homePage.getbookActivityButton().setOnAction(e->navigationService.navigateToNotImplemented(navigationService, "HomePage"));
    }

    public VBox getView(){
        return homePage.getRoot();
    }
}