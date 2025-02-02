package org.example.exception;

public class WrongLoginCredentialsException extends RuntimeException {
  public WrongLoginCredentialsException(String message) {
    super(message);
  }
}
