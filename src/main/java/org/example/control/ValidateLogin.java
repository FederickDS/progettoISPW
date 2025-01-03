package org.example.control;

import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.view.CustomerLoginView;
import org.example.view.ReceptionistLoginView;

import java.util.Optional;
import java.util.logging.Logger;

public class ValidateLogin {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private User user;

    public ValidateLogin(String typeOfLogin) {
        if(typeOfLogin.equalsIgnoreCase("client")){
            //validateClient();
        }else{
            //validateUser();
        }
    }
}

