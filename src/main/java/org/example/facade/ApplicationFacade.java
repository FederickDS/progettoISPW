package org.example.facade;

import javafx.application.Application;
import org.example.application_controller.ValidateLogin;
import org.example.bean.LoginBean;
import org.example.entity.Reservation;
import org.example.entity.Room;
import org.example.entity.User;
import org.example.dao.DaoFactory;
import org.example.factory.ModelBeanFactory;
import org.example.graphic_controller.SessionManager;

import java.util.List;
import java.util.logging.Logger;

import java.sql.SQLException;

public class ApplicationFacade implements ApplicationFacadeInterface {
    private static final String CLIENT = "client";
    private static final String RECEPTIONIST = "receptionist";
    private static final String ESSENTIAL_INFO = "essential_info";
    private static final Logger logger = Logger.getLogger(ApplicationFacade.class.getName());

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

    public static boolean isLoginValid(LoginBean loginBean){
        ValidateLogin validateLogin = new ValidateLogin();
        return validateLogin.validate(loginBean) != null;
    }

    public int getMaxNumberOfParticipants(){
        List<Room> rooms = DaoFactory.getRoomDao().readAll();
        int max = 0;
        for (Room room : rooms) {
            if(max < room.getMaxPeople()){
                max = room.getMaxPeople();
            }
        }
        return max;
    }

    public static void sendReservationEmail(Reservation reservation){
        LoginBean loginBean = ModelBeanFactory.loadLoginBean();
        ValidateLogin validateLogin = new ValidateLogin();
        User user = validateLogin.validate(loginBean);

        String subject = "Conferma Prenotazione #" + reservation.getReservationId();

        String body = "Gentile " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
                + "La tua prenotazione è stata confermata con successo.\n\n"
                + "Dettagli della prenotazione:\n"
                + "ID: " + reservation.getReservationId() + "\n"
                + "Data: " + reservation.getTimetable().getStartDate() + "\n"
                + "Data check out: " + reservation.getTimetable().getEndDate() + "\n"
                + "Importo: " + reservation.getPrice() + " EUR\n\n"
                + "Per qualsiasi domanda, contattaci al nostro servizio clienti.\n\n"
                + "Cordiali saluti,\n"
                + "Reception";


        EmailService.sendEmail(SessionManager.getInstance().getEmail(), subject, body);
    }

    public static void sendGenericEmail(String toEmail, String subject, String body){
        EmailService.sendEmail(toEmail, subject, body);
    }

    public static boolean processPayment(String cardNumber, String cvv, String amount){
        char symbol = amount.charAt(0);
        if(symbol == '€'){
            return PaymentService.processPayment(cardNumber, amount, "EUR",cvv);
        }else if (symbol == '$'){
            return PaymentService.processPayment(cardNumber, amount, "USD",cvv);
        }
        return false;
    }
}