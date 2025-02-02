package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.exception.HashingException;
import org.example.facade.ApplicationFacade;
import org.example.factory.ModelBeanFactory;
import org.example.bean.LoginBean;
import org.example.view.AbstractLoginView;
import org.example.view.ClientLoginView;
import org.example.view.ReceptionistLoginView;

import java.util.logging.Logger;

public class LoginController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private AbstractLoginView loginView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;

    public LoginController(NavigationService navigationService, String previousPage, String nextPage, String typeOfLogin) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        //tipo di view
        if(typeOfLogin.equalsIgnoreCase("client")){
            this.loginView = new ClientLoginView();
        }else{
            this.loginView = new ReceptionistLoginView();
        }
        // Aggiungi gestione eventi
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone di Login
        loginView.getLoginButton().setOnAction(e -> handleLogin());

        // Gestione navigazione indietro
        loginView.getCancelButton().setOnAction(e -> this.navigationService.navigateBack(previousPage,this.navigationService));

        loginView.getRegisterButton().setOnAction(e -> goToRegistration());
    }

    private void handleLogin() {
        LoginBean loginBean = ModelBeanFactory.getLoginBean(loginView);

        if (!loginBean.validateFields(loginView)) {
            return;
        }
        String typeOfLogin = loginBean.getUserType();
        try {
            logger.info(typeOfLogin);
            loginBean.setPassword(ApplicationFacade.encrypt(loginBean.getPassword())); // Potrebbe lanciare HashingException

            if (ApplicationFacade.isLoginValid(loginBean)) {
                logger.info("Login riuscito!");
                SessionManager.getInstance().setCredentials(loginBean);
                navigateToNextPage();
            } else {
                logger.warning("Login fallito. Credenziali non valide.");
                loginView.getErrorMessage().setVisible(true);
                loginView.getErrorMessage().setManaged(true);
            }
        } catch (HashingException e) {
            logger.severe("Errore durante l'hashing della password: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Errore di Accesso","Errore durante il login","Si è verificato un errore di sistema. Riprova più tardi.");
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
    }

    private void navigateToNextPage() {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
        if (nextPage.equalsIgnoreCase("HomePage")) {
            navigationService.navigateToHomePage(this.navigationService);
        } else if (nextPage.equalsIgnoreCase("ServiceSelection")) {
            navigationService.navigateToServiceSelection(this.navigationService);
        }
    }

    private void goToRegistration() {
        navigationService.navigateToRegistration(this.navigationService,previousPage,nextPage,loginView.getType());
    }

    public VBox getRoot(){
        return this.loginView.getRoot();
    }
}
