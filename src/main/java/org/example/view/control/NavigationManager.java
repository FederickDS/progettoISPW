package org.example.view.control;

import javafx.stage.Stage;

public class NavigationManager {
    private final Stage stage;

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    public void navigateToStartupSettings(){
        StartupSettingsController startupSettingsController = new StartupSettingsController(this);
    }

    public void navigateToHomePage() {
        HomePageController homePageController = new HomePageController(this);
        homePageController.loadHomePage();
    }

    public void navigateToServiceSelection() {
        ServiceSelectionController serviceSelectionController = new ServiceSelectionController(this);
        serviceSelectionController.loadServiceSelection();
    }

    protected Stage getStage() {
        return this.stage;
    }
}
