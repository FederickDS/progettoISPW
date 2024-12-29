package org.example.view.control;

import javafx.stage.Stage;
import org.example.control.BookRoom;

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

    public void navigateToBookingRoom(BookRoom bookRoom) {
        new BookingRoomController(this.stage, this, bookRoom).loadBookingRoom();
    }

    protected Stage getStage() {
        return this.stage;
    }
}
