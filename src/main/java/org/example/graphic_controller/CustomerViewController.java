package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.BeanClientEReservationDetails;
import org.example.bean.BeanClientDetails;
import org.example.bean.BeanReservationDetails;
import org.example.facade.ApplicationFacade;
import org.example.view.CustomerView;

import java.util.List;

public class CustomerViewController {
    private CustomerView customerView;
    private BookRoom bookRoom;
    private NavigationService navigationService;
    private BeanClientDetails beanClientDetails;

    public CustomerViewController(NavigationService navigationService) {
        this.customerView = new CustomerView();
        this.bookRoom = new BookRoom();
        this.navigationService = navigationService;

        loadClientAndReservations();
        addEventHandlers();
    }

    private void addEventHandlers(){
        customerView.getBackButton().setOnAction(e -> navigateBack());
        customerView.getChangePasswordButton().setOnAction(e->changeClientPassword());
    }

    private void changeClientPassword(){
        String oldPassword = ApplicationFacade.encrypt(customerView.getOldPasswordField().getText());
        String newPassword = customerView.getNewPasswordField().getText();
        String confirmPassword = customerView.getConfirmPasswordField().getText();
        String passwordBean = beanClientDetails.getPassword();//SHA256

        if(!ckeckPassword(oldPassword, newPassword, confirmPassword, passwordBean)){
            return;
        }
        this.beanClientDetails.setPassword(ApplicationFacade.encrypt(newPassword));
        if(this.bookRoom.updateClientDetails(this.beanClientDetails)){
            customerView.showNewPasswordError("Salvataggio password riuscito!");
        }else{
            customerView.showNewPasswordError("Salvataggio fallito. Riprovare più tardi");
        }
    }

    private boolean ckeckPassword(String oldPassword, String newPassword, String confirmPassword, String passwordBean){
        customerView.hideAllErrors();
        boolean valid = true;
        if(!(oldPassword).equals(passwordBean)){
            customerView.showOldPasswordError();
            valid = false;
        }
        if(!(newPassword).equals(confirmPassword) || (newPassword.length()<8) || (confirmPassword.length()>16)){
            customerView.showNewPasswordError("");
            valid = false;
        }
        return valid;
    }

    private void navigateBack(){
        navigationService.navigateToHomePage(navigationService);
    }

    private void loadClientAndReservations() {
        BeanClientEReservationDetails details = bookRoom.getClientAndReceptionistDetails();
        if (details == null) {
            return;
        }
        this.beanClientDetails = details.getClientDetails();//per modificare la password

        populateClientDetails(beanClientDetails);
        populateReservations(details.getReservationDetails());
    }

    private void populateClientDetails(BeanClientDetails clientDetails) {
        if (clientDetails != null) {
            customerView.getCustomerInfoBox().getChildren().addAll(
                    new javafx.scene.control.Label("Nome: " + clientDetails.getFirstName()),
                    new javafx.scene.control.Label("Cognome: " + clientDetails.getLastName()),
                    new javafx.scene.control.Label("Email: " + clientDetails.getEmail()),
                    new javafx.scene.control.Label("Telefono: " + clientDetails.getPhoneNumber()),
                    new javafx.scene.control.Label("Data di Nascita: " + clientDetails.getBirthDate()),
                    new javafx.scene.control.Label("Codice Fiscale: " + clientDetails.getTaxCode())
            );
        }
    }

    private void populateReservations(List<BeanReservationDetails> reservationDetails) {
        customerView.getReservationsBox().getChildren().clear();
        for (BeanReservationDetails reservation : reservationDetails) {
            if(reservation.getRoomNumber()!=0){
                customerView.getReservationsBox().getChildren().add(
                        new javafx.scene.control.Label(
                                "ID: " + reservation.getReservationId() +
                                        " | Stanza: " + reservation.getRoomNumber() +
                                        " | Prezzo: " + reservation.getPrice() +
                                        " | Stato: " + reservation.getStatus() +
                                        " | Check-in: " + reservation.getTimetable().getStartDate() +
                                        " | Check-out: " + reservation.getTimetable().getEndDate()
                        )
                );

            }
        }
    }

    public VBox getRoot() {
        return customerView.getRoot();
    }
}