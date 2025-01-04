package org.example.view.control;

import org.example.view.HomePage;
import javafx.stage.Stage;

public class HomePageController {

    private final Stage stage;
    private final NavigationService navigationService;
    private HomePage homePage;

    public HomePageController(Stage stage, NavigationService navigationService) {
        this.stage = stage;
        this.navigationService = navigationService;
    }

    public void loadHomePage() {
        // Crea l'HomePage tramite la factory
        this.homePage = new HomePage();

        addEventHandlers();

        // Mostra l'HomePage
        navigationService.display(stage,homePage.getVBox(),"Home page");
    }

    private void addEventHandlers() {
        // Aggiungi il gestore per il pulsante "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationService.navigateToLogin("HomePage","ServiceSelection","client"));
    }
}