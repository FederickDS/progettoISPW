package org.example.app_controller;

import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;

public class ValidateLogin {
    private final User user;

    public ValidateLogin(String typeOfLogin) {
        if(typeOfLogin.equalsIgnoreCase("client")){
            user = new Client();
        }else{
            user = new Receptionist();
        }
    }

    public boolean validate(String username, String password) {
        if(user.getUserType().equals("client")){
            return validateClient(username, password);
        }else{
            return validateReceptionist(username, password);
        }
    }

    public boolean validateClient(String username, String password) {
        return true;
    }

    public boolean validateReceptionist(String username, String password) {
        return false;
    }
}

