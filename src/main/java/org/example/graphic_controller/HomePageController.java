package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.HomePage;

public class HomePageController {
    private final HomePage homePage;

    public HomePageController() {
        // Crea l'HomePage tramite la factory
        this.homePage = new HomePage();

        addEventHandlers();
    }

    private void addEventHandlers() {
        // Aggiungi il gestore per il pulsante "Prenota Stanza"
        homePage.getBookRoomButton().setOnAction(e -> NavigationManager.getInstance().navigateToLogin("HomePage","ServiceSelection","client"));
    }

    public VBox getView(){
        return homePage.getRoot();
    }
}