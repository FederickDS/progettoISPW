package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;

public interface NavigationService {
    void navigateToHomePage(NavigationService navigationService);
    void navigateToServiceSelection(NavigationService navigationService);
    void navigateToStartupSettings(NavigationService navigationService);
    void navigateToBookingRoom(NavigationService navigationService, BookRoom bookRoom);
    void navigateToLogin(NavigationService navigationService, String previousPage, String nextPage, String type);
    void navigateToRegistration(NavigationService navigationService, String previousPage, String nextPage, String userType);
    void navigateToRoomBookingOptions(NavigationService navigationService, String previousPage, String nextPage);
    void navigateToEssentialInfo(NavigationService navigationService, String previousPage, String nextPage);
    void navigateToNotImplemented(NavigationService navigationService, String previousPage);
    void navigateBack(String previousPage, NavigationService navigationService);
    void navigateToReservationPayment(NavigationService navigationService, BookRoom bookRoom);
    void navigateToCustomerView(NavigationService navigationService);
    void navigateToReceptionistView(NavigationService navigationService);
    void display(VBox root, String title);
    void setInterfaceOption(String interfaceOption);
    }