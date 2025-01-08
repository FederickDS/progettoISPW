package org.example.dao;

import org.example.entity.Receptionist;
import java.sql.*;

public class ReceptionistDaoDB implements GenericDao<Receptionist> {
    private final Connection connection;

    public ReceptionistDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Receptionist receptionist) throws SQLException {
        String sql = "INSERT INTO receptionist (first_name, last_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, receptionist.getFirstName());
            ps.setString(2, receptionist.getLastName());
            ps.setString(3, receptionist.getEmail());
            ps.setString(4, receptionist.getPhoneNumber());
            ps.setString(5, receptionist.getPassword());
            ps.executeUpdate();
        }
    }

    @Override
    public Receptionist read(String email) throws SQLException {
        String sql = "SELECT * FROM receptionist WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Receptionist(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null; // Nessun receptionist trovato
    }

    @Override
    public void update(Receptionist receptionist) throws SQLException {
        String sql = "UPDATE receptionist SET first_name = ?, last_name = ?, phone_number = ?, password = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, receptionist.getFirstName());
            ps.setString(2, receptionist.getLastName());
            ps.setString(3, receptionist.getPhoneNumber());
            ps.setString(4, receptionist.getPassword());
            ps.setString(5, receptionist.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String email) throws SQLException {
        String sql = "DELETE FROM receptionist WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
}
