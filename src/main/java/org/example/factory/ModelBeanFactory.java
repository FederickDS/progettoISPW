package org.example.factory;

import org.example.bean.*;
import org.example.entity.BaseModel;
import org.example.entity.Client;
import org.example.entity.Reservation;
import org.example.entity.User;
import org.example.facade.ApplicationFacadeInterface;
import org.example.graphic_controller.SessionManager;
import org.example.view.AbstractLoginView;
import org.example.view.AbstractLoginAlternativeView;
import org.example.view.AbstractRegistrationView;
import org.example.view.BookingRoom;

import java.math.RoundingMode;

public class ModelBeanFactory {

    private ModelBeanFactory() {
        // Costruttore privato per nascondere il costruttore pubblico implicito
    }

    public static LoginBean getLoginBean(AbstractLoginView loginView) {
        LoginBean loginBean = new LoginBean();
        loginBean.populateFromView(loginView);
        return loginBean;
    }

    public static LoginBean getLoginBean(AbstractLoginAlternativeView loginView) {
        LoginBean loginBean = new LoginBean();
        loginBean.populateFromAlternativeView(loginView);
        return loginBean;
    }

    public static LoginBean loadLoginBean() {
        if (SessionManager.getInstance().getEmail() == null) {
            return null;
        }
        LoginBean loginBean = new LoginBean();
        loginBean.setEmail(SessionManager.getInstance().getEmail());
        loginBean.setPassword(SessionManager.getInstance().getPassword());
        loginBean.setUserType(SessionManager.getInstance().getType());
        return loginBean;
    }

    public static UserRegistrationBean getUserRegistrationBean(AbstractRegistrationView registrationView) {
        return new UserRegistrationBean(registrationView);
    }

    public static PaymentBean toPaymentBean(Reservation reservation) {
        if (reservation == null || reservation.getRoom() == null) {
            throw new IllegalArgumentException("La prenotazione e la stanza non possono essere null.");
        }

        int roomNumber = reservation.getRoom().getRoomNumber();
        String price = reservation.getPrice() != null ? "€" + reservation.getPrice().setScale(2, RoundingMode.HALF_UP) : "Non calcolato";
        String paymentDeadline = reservation.getPaymentDeadline();
        int reservationID = reservation.getReservationId();

        return new PaymentBean(roomNumber, price, paymentDeadline, reservationID);
    }

    public static BeanClientDetails getBeanClientDetails(Client client) {
        BeanClientDetails beanClientDetails = new BeanClientDetails();
        beanClientDetails.setBirthDate(client.getBirthDate());
        beanClientDetails.setEmail(client.getEmail());
        beanClientDetails.setFirstName(client.getFirstName());
        beanClientDetails.setLastName(client.getLastName());
        beanClientDetails.setPhoneNumber(client.getPhoneNumber());
        beanClientDetails.setTaxCode(client.getTaxCode());
        beanClientDetails.setPassword(client.getPassword());
        return beanClientDetails;
    }

    public static BeanReservationDetails getBeanReservationDetails(Reservation reservation) {
        BeanReservationDetails beanReservationDetails = new BeanReservationDetails();
        beanReservationDetails.setReservationId(reservation.getReservationId());
        beanReservationDetails.setTimetable(reservation.getTimetable());
        beanReservationDetails.setRoomNumber(reservation.getRoom().getRoomNumber());
        beanReservationDetails.setPrice(reservation.getPrice());
        beanReservationDetails.setStatus(reservation.getStatus().toString());

        beanReservationDetails.setFreeServices(
                reservation.getFreeServices().stream().map(BaseModel::getName).toList()
        );

        beanReservationDetails.setFreeActivities(
                reservation.getFreeActivities().stream().map(BaseModel::getName).toList()
        );

        beanReservationDetails.setClientEmails(
                reservation.getClients().stream().map(User::getEmail).toList()
        );

        return beanReservationDetails;
    }

    public static BookingRoomBean setBookingRoomBean(BookingRoom bookingRoom, ApplicationFacadeInterface applicationFacade) {
        BookingRoomBean bookingRoomBean = new BookingRoomBean();
        bookingRoomBean.populateView(bookingRoom, applicationFacade);
        return bookingRoomBean;
    }
}
