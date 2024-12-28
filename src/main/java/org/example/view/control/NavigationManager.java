package org.example.view.control;

import javafx.stage.Stage;

public class NavigationManager {
    private final Stage stage;
    private StartupSettingsController startupSettingsController;
    private HomePageController homePageController;
    private ServiceSelectionController serviceSelectionController;

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    public void navigateToStartupSettings(){
        if(startupSettingsController == null){
            startupSettingsController = new StartupSettingsController(this.stage);
        }
        startupSettingsController.loadStartupSettings();
    }

    public void navigateToHomePage() {
        if(homePageController == null){
            homePageController = new HomePageController(this.stage);
        }
        homePageController.loadHomePage();
    }

    public void navigateToServiceSelection() {
        if(serviceSelectionController == null){
            serviceSelectionController = new ServiceSelectionController(this.stage);
        }
        serviceSelectionController.loadServiceSelection();
    }

    protected Stage getStage() {
        return this.stage;
    }
}
