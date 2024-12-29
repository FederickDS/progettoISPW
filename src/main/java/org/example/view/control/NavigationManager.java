package org.example.view.control;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import org.example.control.BookRoom;
import org.example.entity.StartupSettingsEntity;

import javafx.scene.layout.VBox;


public class NavigationManager implements NavigationService{
    private final Stage stage;
    private Logger logger;

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

    public void display(Stage stage, VBox root, String title) {
        try {
            Scene scene = new Scene(root);
            //modifica colori
            scene.getStylesheets().clear();
            String styleCSS = StartupSettingsEntity.getInstance().getCSSStyle();
            scene.getStylesheets().add(getClass().getResource(styleCSS).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        }catch (Exception e){
            this.logger.warning("Date non valide. Selezionare entrambe le date.");
        }
    }

    protected Stage getStage() {
        return this.stage;
    }
}
