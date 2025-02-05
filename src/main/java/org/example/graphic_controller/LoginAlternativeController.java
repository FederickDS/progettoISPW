package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.exception.HashingException;
import org.example.exception.WrongLoginCredentialsException;
import org.example.facade.ApplicationFacade;
import org.example.factory.ModelBeanFactory;
import org.example.bean.LoginBean;
import org.example.view.LoginAlternativeView;
import org.example.view.AbstractLoginAlternativeView;

import java.util.logging.Logger;

public class LoginAlternativeController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private AbstractLoginAlternativeView loginView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;

    public LoginAlternativeController(NavigationService navigationService, String previousPage, String nextPage) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.loginView = new LoginAlternativeView();

        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone di Login
        loginView.getConfirmButton().setOnAction(e -> handleLogin());

        // Gestione navigazione indietro
        loginView.getCancelButton().setOnAction(e -> this.navigationService.navigateBack(previousPage, this.navigationService));

        loginView.getRegisterButton().setOnAction(e -> goToRegistration());
    }

    private void handleLogin() {
        // Verifica se è stato selezionato un tipo di login
        if (loginView.getLoginTypeGroup().getSelectedToggle() == null) {
            ApplicationFacade.showErrorMessage("Errore", "Tipo di login non selezionato", "Seleziona Cliente o Receptionist prima di procedere.");
            return;
        }

        // Determina il tipo di login selezionato
        String typeOfLogin = loginView.getClientLoginOption().isSelected() ? "client" : "receptionist";

        // Creazione e popolamento del bean di login
        LoginBean loginBean = ModelBeanFactory.getLoginBean(loginView);
        loginBean.setUserType(typeOfLogin);

        if (!loginBean.validateFields(loginView)) {
            return;
        }

        try {
            logger.info("Tentativo di login per utente: " + typeOfLogin);
            loginBean.setPassword(ApplicationFacade.encrypt(loginBean.getPassword()));

            if (ApplicationFacade.isLoginValid(loginBean)) {
                logger.info("Login riuscito per " + typeOfLogin);
                SessionManager.getInstance().setCredentials(loginBean);
                navigateToNextPage();
            } else {
                throw new WrongLoginCredentialsException("Email o password non validi");
            }
        } catch (WrongLoginCredentialsException e) {
            loginView.getErrorMessage().setVisible(true);
            loginView.getErrorMessage().setManaged(true);
            logger.warning("Errore di login: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Accesso negato", "Credenziali errate", e.getMessage());
        } catch (HashingException e) {
            logger.severe("Errore durante l'hashing della password: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Errore di Accesso", "Errore durante il login", "Si è verificato un errore di sistema. Riprova più tardi.");
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
    }

    private void navigateToNextPage() {
        if (nextPage.equalsIgnoreCase("HomePage")) {
            navigationService.navigateToHomePage(this.navigationService);
        } else if (nextPage.equalsIgnoreCase("ServiceSelection")) {
            navigationService.navigateToServiceSelection(this.navigationService);
        } else if(nextPage.equalsIgnoreCase("HomePageAlternative")){
            navigationService.navigateToHomePageAlternative(this.navigationService);
        }
    }

    private void goToRegistration() {
        navigationService.navigateToRegistrationAlternative(this.navigationService, previousPage, nextPage, loginView.getClientLoginOption().isSelected() ? "client" : "receptionist");
    }

    public VBox getRoot() {
        return this.loginView.getRoot();
    }
}
