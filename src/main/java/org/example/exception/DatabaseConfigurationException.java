package org.example.exception;

public class DatabaseConfigurationException extends RuntimeException {
    public DatabaseConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
    public DatabaseConfigurationException(String message) {
        super(message);
    }
}