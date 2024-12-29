package org.example.view.control;

import org.example.factory.GUIFactory;
import org.example.view.HomePage;
import org.example.entity.StartupSettingsEntity;
import javafx.stage.Stage;

public class HomePageController {

    private final Stage stage;
    private final NavigationService navigationService;

    public HomePageController(Stage stage, NavigationService navigationService) {
        this.stage = stage;
        this.navigationService = navigationService;
    }

    public void loadHomePage() {
        // Seleziona la factory appropriata in base all'opzione salvata
        GUIFactory factory = StartupSettingsEntity.getInstance().typeOfGUI();

        // Crea l'HomePage tramite la factory
        HomePage homePage = new HomePage();

        // Aggiungi il gestore per il pulsante "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationService.navigateToServiceSelection());

        // Mostra l'HomePage
        homePage.display(stage);
    }
}