package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.view.NotImplementedView;
import java.util.logging.Logger;

public class NotImplementedController {
    private final NotImplementedView notImplementedView;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final NavigationService navigationService;
    private final String returnPage;

    public NotImplementedController(NavigationService navigationService, String returnPage) {
        this.navigationService = navigationService;
        this.returnPage = returnPage;
        notImplementedView = new NotImplementedView();
        addEventHandlers();
    }

    private void addEventHandlers() {
        notImplementedView.getBackButton().setOnAction(e -> handleBack());
    }

    private void handleBack() {
        logger.info("Tornando alla pagina precedente ");
        navigationService.navigateBack(returnPage, navigationService);
    }

    public VBox getRoot() {
        return notImplementedView.getRoot();
    }
}