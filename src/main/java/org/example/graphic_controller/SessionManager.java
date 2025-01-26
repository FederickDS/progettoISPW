package org.example.graphic_controller;

public class SessionManager {
    private static SessionManager instance;
    private String email;
    private String password;
    private String type;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCredentials(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void clearSession() {
        this.email = null;
        this.password = null;
        this.type = null;
    }
}