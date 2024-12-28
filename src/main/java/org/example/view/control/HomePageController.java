package org.example.view.control;

import org.example.factory.GUIFactory;
import org.example.view.HomePage;
import org.example.entity.StartupSettingsEntity;
import javafx.stage.Stage;

public class HomePageController {

    private final NavigationManager navigationManager;

    public HomePageController(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public void loadHomePage() {
        // Seleziona la factory appropriata in base all'opzione salvata
        GUIFactory factory = StartupSettingsEntity.getInstance().typeOfGUI();

        // Crea l'HomePage tramite la factory
        HomePage homePage = factory.createHomePage();

        // Aggiungi il gestore per il pulsante "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> navigationManager.navigateToServiceSelection());

        // Mostra l'HomePage
        homePage.display(navigationManager.getStage());
    }
    private void loadServiceSelectionPage() {
        // Passa il controllo al ServiceSelectionController
        navigationManager.navigateToServiceSelection();
    }
}