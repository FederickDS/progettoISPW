package org.example.view.control;

import org.example.view.HomePage;
import javafx.stage.Stage;

public class HomePageController {

    private final Stage stage;
    private final NavigationService navigationService;

    public HomePageController(Stage stage, NavigationService navigationService) {
        this.stage = stage;
        this.navigationService = navigationService;
    }

    public void loadHomePage() {
        // Crea l'HomePage tramite la factory
        HomePage homePage = new HomePage();

        // Aggiungi il gestore per il pulsante "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationService.navigateToServiceSelection());

        // Mostra l'HomePage
        navigationService.display(stage,homePage.getRoot(),"Home page");
    }
}