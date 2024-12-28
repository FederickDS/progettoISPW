package org.example.view.control;

import javafx.stage.Stage;

public class NavigationManager implements NavigationService{
    private final Stage stage;

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void navigateToStartupSettings(){
        new StartupSettingsController(this.stage, this).loadStartupSettings();
    }

    @Override
    public void navigateToHomePage() {
        new HomePageController(this.stage, this).loadHomePage();
    }

    @Override
    public void navigateToServiceSelection() {
        new ServiceSelectionController(this.stage, this).loadServiceSelection();
    }

    protected Stage getStage() {
        return this.stage;
    }
}
