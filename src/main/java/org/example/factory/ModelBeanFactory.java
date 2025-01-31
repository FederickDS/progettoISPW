package org.example.factory;

import org.example.bean.*;
import org.example.entity.BaseModel;
import org.example.entity.Client;
import org.example.entity.Reservation;
import org.example.entity.User;
import org.example.graphic_controller.SessionManager;
import org.example.view.AbstractLoginView;
import org.example.view.AbstractRegistrationView;

import java.math.BigDecimal;
import java.util.stream.Collectors;

// Classe centrale che mantiene i riferimenti a tutti gli adapter
public class ModelBeanFactory {
    public static PaymentBean toPaymentBean(Reservation reservation) {
        if (reservation == null || reservation.getRoom() == null) {
            throw new IllegalArgumentException("La prenotazione e la stanza non possono essere null.");
        }

        int roomNumber = reservation.getRoom().getRoomNumber();
        String price = reservation.getPrice() != null ? "€" + reservation.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP) : "Non calcolato";
        String paymentDeadline = reservation.getPaymentDeadline();

        return new PaymentBean(roomNumber, price, paymentDeadline);
    }
    public Reservation convertToEntity(PaymentBean bean) {
        throw new UnsupportedOperationException("La conversione inversa non è supportata direttamente.");
    }

    public static UserRegistrationBean getUserRegistrationBean(AbstractRegistrationView registrationView) {
        return new UserRegistrationBean(registrationView);
    }

    public static LoginBean getLoginBean(AbstractLoginView loginView) {
        LoginBean loginBean = new LoginBean();
        loginBean.populateFromView(loginView);
        return loginBean;
    }

    public static LoginBean loadLoginBean(){
        if (SessionManager.getInstance().getEmail() == null) {
            return null;
        }
        LoginBean loginBean = new LoginBean();
        loginBean.setEmail(SessionManager.getInstance().getEmail());
        loginBean.setPassword(SessionManager.getInstance().getPassword());
        loginBean.setUserType(SessionManager.getInstance().getType());
        return loginBean;
    }

    public static BeanClientDetails getBeanClientDetails(Client client) {
        BeanClientDetails beanClientDetails = new BeanClientDetails();
        beanClientDetails.setBirthDate(client.getBirthDate());
        beanClientDetails.setEmail(client.getEmail());
        beanClientDetails.setFirstName(client.getFirstName());
        beanClientDetails.setLastName(client.getLastName());
        beanClientDetails.setPhoneNumber(client.getPhoneNumber());
        beanClientDetails.setFirstName(client.getTaxCode());
        beanClientDetails.setTaxCode(client.getTaxCode());
        return beanClientDetails;
    }

    public static BeanReservationDetails getBeanReservationDetails(Reservation reservation) {
        BeanReservationDetails beanReservationDetails = new BeanReservationDetails();
        System.out.println(reservation.getReservationId());
        beanReservationDetails.setReservationId(reservation.getReservationId());
        beanReservationDetails.setTimetable(reservation.getTimetable());
        beanReservationDetails.setRoomNumber(reservation.getRoom().getRoomNumber());
        beanReservationDetails.setPrice(reservation.getPrice());
        beanReservationDetails.setStatus(reservation.getStatus().toString());

        beanReservationDetails.setFreeServices(
                reservation.getFreeServices().stream().map(BaseModel::getName).collect(Collectors.toList())
        );

        beanReservationDetails.setFreeActivities(
                reservation.getFreeActivities().stream().map(BaseModel::getName).collect(Collectors.toList())
        );

        beanReservationDetails.setClientEmails(
                reservation.getClients().stream().map(User::getEmail).collect(Collectors.toList())
        );

        return beanReservationDetails;
    }

}
