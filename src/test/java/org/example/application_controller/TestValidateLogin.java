package org.example.application_controller;

import org.example.bean.LoginBean;
import org.example.dao.DaoFactory;
import org.example.dao.GenericDao;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;
import org.example.exception.WrongLoginCredentialsException;
import org.example.facade.ApplicationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestValidateLogin {

    @Mock
    private GenericDao<Client> clientDao;

    @Mock
    private GenericDao<Receptionist> receptionistDao;

    @InjectMocks
    private ValidateLogin validateLogin;

    private LoginBean validClientLogin;
    private LoginBean invalidLogin;
    private LoginBean emptyLogin;
    private Client mockClient;

    @BeforeEach
    void setUp() throws SQLException {
        String password = "password123";
        String encryptedPassword = ApplicationFacade.encrypt(password);

        // Crea LoginBean per il client valido
        validClientLogin = new LoginBean();
        validClientLogin.setPassword(encryptedPassword);
        validClientLogin.setUserType("client");
        validClientLogin.setEmail("client@example.com");

        // Crea un Client valido con tutti gli attributi necessari
        mockClient = new Client();
        mockClient.setEmail("client@example.com");
        mockClient.setPassword(encryptedPassword);
        mockClient.setFirstName("Mario");
        mockClient.setLastName("Rossi");
        mockClient.setPhoneNumber("1234567890");
        mockClient.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        mockClient.setTaxCode("RSSMRA90A01H501Z");

        // Mocking di clientDao.read(email)
        lenient().when(clientDao.read("client@example.com")).thenReturn(mockClient);
        lenient().when(clientDao.read("wrong@example.com")).thenReturn(null);
        lenient().when(clientDao.read("")).thenReturn(null);
        lenient().when(clientDao.read(null)).thenReturn(null);

        // Crea un LoginBean con credenziali errate
        invalidLogin = new LoginBean();
        invalidLogin.setPassword(ApplicationFacade.encrypt("wrongpassword"));
        invalidLogin.setUserType("client");
        invalidLogin.setEmail("wrong@example.com");

        // Crea un LoginBean con email vuota
        emptyLogin = new LoginBean();
        emptyLogin.setUserType("client");
    }

    @Test
    void testValidCredentials() throws SQLException {
        // Stampa le password per debug
        System.out.println("Password LoginBean: " + validClientLogin.getPassword());
        System.out.println("Password Client: " + mockClient.getPassword());

        assertNotNull(validClientLogin, "L'utente non dovrebbe essere null");
        assertEquals("client@example.com", validClientLogin.getEmail(), "L'email dovrebbe essere corretta");
        assertEquals(mockClient.getUserType(), validClientLogin.getUserType(), "Il nome dovrebbe corrispondere");
        assertEquals(mockClient.getPassword(), validClientLogin.getPassword(), "Il cognome dovrebbe corrispondere");
    }

    @Test
    void testInvalidCredentials() throws SQLException {
        Exception exception = assertThrows(WrongLoginCredentialsException.class, () -> {
            validateLogin.validate(invalidLogin);
        });

        assertEquals("Credenziali non corrette", exception.getMessage());
    }

    @Test
    void testEmptyFields() {
        assertNull(validateLogin.validate(emptyLogin), "Il login con campi vuoti dovrebbe restituire null");
    }
}
