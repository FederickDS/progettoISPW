package org.example.factory;

import org.example.bean.LoginBean;
import org.example.bean.PaymentBean;
import org.example.bean.UserRegistrationBean;
import org.example.entity.Reservation;
import org.example.graphic_controller.SessionManager;
import org.example.view.AbstractLoginView;
import org.example.view.AbstractRegistrationView;

import java.math.BigDecimal;

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

}
