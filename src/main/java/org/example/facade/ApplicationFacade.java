package org.example.facade;

import org.example.application_controller.ValidateLogin;
import org.example.bean.LoginBean;
import org.example.entity.User;
import org.example.dao.DaoFactory;
import org.example.factory.ModelBeanFactory;

import java.util.logging.Logger;

import java.sql.SQLException;

public class ApplicationFacade {
    private static final String CLIENT = "client";
    private static final String RECEPTIONIST = "receptionist";
    private static final String ESSENTIAL_INFO = "essential_info";
    public static Logger logger = Logger.getLogger(ApplicationFacade.class.getName());

    /**
     * Esegue l'hashing della password usando SHA-256.
     * @param password La password da criptare.
     * @return La password hashata in formato String.
     */
    public static String encrypt(String password) {
        return User.hashWithSHA256(password);
    }

    /**
     * Ottiene la scadenza del pagamento per una prenotazione specifica.
     * @param reservationId L'ID della prenotazione.
     * @return La data di scadenza del pagamento.
     */
    public static String getPaymentDeadline(String reservationId) {
        try {
            return DaoFactory.getReservationDao().read(reservationId).getPaymentDeadline();
        }catch (SQLException e){
            logger.info("Id ricevuto non esiste in memoria, o non siamo connessi alla rete");
        }
        return null;
    }

    public static String checkLoginStatus(){
        try {
            LoginBean loginBean = ModelBeanFactory.loadLoginBean();
            ValidateLogin validateLogin = new ValidateLogin();
            if (validateLogin.validate(loginBean) == null) {
                return null;
            } else {
                assert loginBean != null;
                if (CLIENT.equalsIgnoreCase(loginBean.getUserType())) {
                    return CLIENT;
                } else if (RECEPTIONIST.equalsIgnoreCase(loginBean.getUserType())) {
                    return RECEPTIONIST;
                } else if (ESSENTIAL_INFO.equalsIgnoreCase(loginBean.getUserType())) {
                    return ESSENTIAL_INFO;
                }
            }
            return null;
        } catch (NullPointerException e) {
            logger.info("accesso a loginBean fallito");
        }
        return null;
    }

    public static User getUserFromLogin(){
        ValidateLogin login = new ValidateLogin();
        return login.validate(ModelBeanFactory.loadLoginBean());
    }
}