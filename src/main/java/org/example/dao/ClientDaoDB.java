package org.example.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.entity.Client;
import org.example.exception.UserAlreadyInsertedException;
import org.example.exception.WrongLoginCredentialsException;

public class ClientDaoDB implements GenericDao<Client> {
    private final Connection connection;

    public ClientDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Client client) throws SQLException {
        if (phoneNumberExists(client.getPhoneNumber())) {
            throw new UserAlreadyInsertedException("Il numero di telefono è già registrato");
        }

        String sql = "INSERT INTO Client (first_name, last_name, email, phone_number, password, birth_date, tax_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getPhoneNumber());
            ps.setString(5, client.getPassword());

            // Verifica se birthDate è null e usa un valore predefinito se necessario
            if (client.getBirthDate() != null) {
                ps.setDate(6, Date.valueOf(client.getBirthDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setString(7, client.getTaxCode());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UserAlreadyInsertedException("Esiste già il cliente con questa email");
        }
    }

    private boolean phoneNumberExists(String phoneNumber) throws SQLException {
        String sql = "SELECT 1 FROM Client WHERE phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Client read(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Chiave non valida per la ricerca del Client");
        }

        String email = (String) keys[0];
        String sql = "SELECT first_name, last_name, email, phone_number, password, birth_date, tax_code FROM Client WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Controllo se birth_date è NULL prima di chiamare toLocalDate()
                    Date birthDateSql = rs.getDate("birth_date");
                    LocalDate birthDate = (birthDateSql != null) ? birthDateSql.toLocalDate() : null;

                    return new Client(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            birthDate, // Valore che può essere null
                            rs.getString("tax_code")
                    );
                } else {
                    throw new WrongLoginCredentialsException("Credenziali non corrette");
                }
            }
        }
    }

    @Override
    public void update(Client client) throws SQLException {
        String sql = "UPDATE Client SET first_name = ?, last_name = ?, phone_number = ?, password = ?, birth_date = ?, tax_code = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getPhoneNumber());
            ps.setString(4, client.getPassword());
            ps.setDate(5, Date.valueOf(client.getBirthDate()));
            ps.setString(6, client.getTaxCode());
            ps.setString(7, client.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Chiave non valida per l'eliminazione del Client");
        }

        String email = (String) keys[0];
        String sql = "DELETE FROM Client WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Client> readAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT first_name, last_name, email, phone_number, password, birth_date, tax_code FROM Client";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clients.add(new Client(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("tax_code")
                ));
            }
        } catch (SQLException e) {
            throw new UserAlreadyInsertedException("Lista non recuperabile, ", e);
        }
        return clients;
    }
}
