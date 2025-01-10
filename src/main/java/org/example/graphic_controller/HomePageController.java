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
        // Aggiungi il gestore per il pulsante "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationService.navigateToLogin(this.navigationService, "HomePage","ServiceSelection","client"));
    }

    public VBox getView(){
        return homePage.getRoot();
    }
}