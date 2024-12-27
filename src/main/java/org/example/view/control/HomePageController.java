package org.example.view.control;

import org.example.factory.GUIFactory;
import org.example.factory.GUIBW;
import org.example.factory.GUIColored;
import org.example.view.HomePage;
import org.example.entity.StartupSettingsEntity;
import javafx.stage.Stage;

public class HomePageController {

    private final Stage stage;

    public HomePageController(Stage stage) {
        this.stage = stage;
    }

    public void loadHomePage() {
        // Ottieni il valore di interfaceOption da StartupSettingsEntity
        String interfaceOption = StartupSettingsEntity.getInstance().getInterfaceOption();

        // Seleziona la factory appropriata in base all'opzione salvata
        GUIFactory factory;
        if ("BW".equalsIgnoreCase(interfaceOption)) {
            factory = new GUIBW();
        } else {
            factory = new GUIColored();
        }

        // Crea l'HomePage tramite la factory
        HomePage homePage = factory.createHomePage();

        // Mostra l'HomePage
        homePage.display(stage);
    }
}
