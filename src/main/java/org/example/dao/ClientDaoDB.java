package org.example.dao;

import java.sql.*;

import org.example.entity.Client;

public class ClientDaoDB implements GenericDao<Client> {
    private final Connection connection;

    public ClientDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Client client) throws SQLException {
        // Controlla se il numero di telefono esiste già
        if (phoneNumberExists(client.getPhoneNumber())) {
            throw new SQLException("Il numero di telefono è già registrato");
        }

        String sql = "INSERT INTO Client (first_name, last_name, email, phone_number, password, birth_date, tax_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getPhoneNumber());
            ps.setString(5, client.getPassword());
            ps.setDate(6, java.sql.Date.valueOf(client.getBirthDate()));
            ps.setString(7, client.getTaxCode());
            ps.executeUpdate();
        }
    }

    // Metodo per verificare se un numero di telefono è già presente
    private boolean phoneNumberExists(String phoneNumber) throws SQLException {
        String sql = "SELECT 1 FROM Client WHERE phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Restituisce true se esiste almeno una riga, esiste uno nuovo
            }
        }
    }


    @Override
    public Client read(String email) throws SQLException {
        String sql = "SELECT first_name, last_name, email, phone_number, password, birth_date, tax_code FROM Client WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password"),
                            rs.getDate("birth_date").toLocalDate(),
                            rs.getString("tax_code")
                    );
                }
            }
        }
        return null; // Nessun client trovato
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
    public void delete(String email) throws SQLException {
        String sql = "DELETE FROM Client WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
}
