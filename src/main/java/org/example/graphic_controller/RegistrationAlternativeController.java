package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.exception.HashingException;
import org.example.exception.UserAlreadyInsertedException;
import org.example.facade.ApplicationFacade;
import org.example.factory.ModelBeanFactory;
import org.example.bean.UserRegistrationBean;
import org.example.view.RegistrationViewAlternative;
import org.example.application_controller.UserRegistrationController;

import java.util.logging.Logger;

public class RegistrationAlternativeController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private RegistrationViewAlternative registrationView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;
    private final String userType;

    public RegistrationAlternativeController(NavigationService navigationService, String previousPage, String nextPage, String userType) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.userType = userType;

        this.registrationView = new RegistrationViewAlternative() {
            @Override
            protected String getTitleText() {
                return "Registrazione " + (userType.equals("client") ? "Cliente" : "Receptionist");
            }

            @Override
            public void hideSpecificErrors() {
                // Implementazione specifica per nascondere errori
            }
        };

        addEventHandlers();
    }

    private void addEventHandlers() {
        registrationView.getConfirmButton().setOnAction(e -> handleRegistration());
        registrationView.getBackButton().setOnAction(e -> navigationService.navigateBack(previousPage, navigationService));
        registrationView.getLoginButton().setOnAction(e -> navigationService.navigateToLoginAlternative(navigationService, previousPage, nextPage));
    }

    private void handleRegistration() {
        UserRegistrationController appController = new UserRegistrationController();
        UserRegistrationBean userRegistrationBean = ModelBeanFactory.getUserRegistrationBean(registrationView);
        userRegistrationBean.setUserType(userType);

        if (!userRegistrationBean.validateAllFields(registrationView)) {
            return;
        }

        try {
            String result = appController.registerUser(userRegistrationBean);

            switch (result) {
                case "success" -> navigateToNextPage();
                case "error:phone_exists" -> registrationView.getPhoneNumberError().setText("Il numero di telefono è già registrato.");
                case "error:database_error" -> registrationView.getDatabaseError().setText("Errore durante la registrazione. Riprova più tardi.");
                default -> registrationView.getDatabaseError().setText("Errore sconosciuto.");
            }
        } catch (HashingException e) {
            logger.severe("Errore durante l'hashing della password: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Errore di registrazione","Errore durante la registrazione","Errore di sistema durante la registrazione. Riprova più tardi.");
        } catch (UserAlreadyInsertedException e) {
            logger.severe("Errore durante il salvataggio dell'utente: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Dati duplicati","Controlla i campi inseriti",e.getMessage());
        }
    }

    private void navigateToNextPage() {
        switch (nextPage) {
            case "HomePage" -> navigationService.navigateToHomePage(this.navigationService);
            case "ServiceSelection" -> navigationService.navigateToServiceSelection(this.navigationService);
            default -> logger.warning("Pagina successiva non definita");
        }
    }

    public VBox getRoot() {
        return this.registrationView.getRoot();
    }
}
