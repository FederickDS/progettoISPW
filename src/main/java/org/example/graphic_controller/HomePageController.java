package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.ValidateLogin;
import org.example.bean.LoginBean;
import org.example.factory.ModelBeanFactory;
import org.example.view.ClientHomePage;
import org.example.view.HomePage;
import org.example.view.ReceptionistHomePage;

public class HomePageController<T> {
    private T homePage;
    private final NavigationService navigationService;
    private static final String HOME_PAGE = "HomePage";
    private static final String CLIENT = "client";
    private static final String RECEPTIONIST = "receptionist";

    public HomePageController(NavigationService navigationService) {
        this.navigationService = navigationService;
        determineHomePage();
    }

    private void determineHomePage() {
        LoginBean loginBean = ModelBeanFactory.loadLoginBean();
        ValidateLogin validateLogin = new ValidateLogin();
        if (validateLogin.validate(loginBean) == null) { //credenziali errate o nulle
            homePage = (T) new HomePage();
        } else if (CLIENT.equalsIgnoreCase(loginBean.getUserType())) {
            System.out.println(loginBean.getUserType());
            System.out.println(loginBean.getPassword());
            homePage = (T) new ClientHomePage();
        } else if (RECEPTIONIST.equalsIgnoreCase(loginBean.getUserType())) {
            System.out.println(loginBean.getUserType());
            System.out.println(loginBean.getPassword());
            homePage = (T) new ReceptionistHomePage();
        }
        addEventHandlers(homePage);
    }

    private void addEventHandlers(Object homePage) {
        if (homePage instanceof HomePage hp) {
            hp.getBookRoomButton().setOnAction(e -> navigationService.navigateToRoomBookingOptions(navigationService, HOME_PAGE, "ServiceSelection"));
            hp.getloginButton().setOnAction(e -> navigationService.navigateToLogin(navigationService, HOME_PAGE, HOME_PAGE, CLIENT));
            hp.getBookActivityButton().setOnAction(e -> navigationService.navigateToNotImplemented(navigationService, HOME_PAGE));
            hp.getReceptionistAccessButton().setOnAction(e -> navigationService.navigateToLogin(navigationService, HOME_PAGE, HOME_PAGE, RECEPTIONIST));
        } else if (homePage instanceof ClientHomePage chp) {
            chp.getBookRoomButton().setOnAction(e -> navigationService.navigateToServiceSelection(navigationService));
            chp.getBookActivityButton().setOnAction(e -> navigationService.navigateToNotImplemented(navigationService,HOME_PAGE));
            chp.getUserInfoButton().setOnAction(e -> navigationService.navigateToNotImplemented(navigationService,HOME_PAGE));
            chp.getLogoutButton().setOnAction(e -> {
                SessionManager.getInstance().clearSession();
                navigationService.navigateToHomePage(navigationService);
            });
        } else if (homePage instanceof ReceptionistHomePage rhp) {
            rhp.getManageBookingsButton().setOnAction(e -> navigationService.navigateToHomePage(navigationService));
            rhp.getManageActivitiesButton().setOnAction(e -> navigationService.navigateToHomePage(navigationService));
        }
    }

    public VBox getRoot(){
        if(homePage instanceof HomePage){
            return ((HomePage) homePage).getRoot();
        }else if(homePage instanceof ClientHomePage){
            return ((ClientHomePage) homePage).getRoot();
        }else if(homePage instanceof ReceptionistHomePage){
            return ((ReceptionistHomePage) homePage).getRoot();
        }
        return null;
    }
}