package org.example.dao;

import org.example.entity.Receptionist;
import org.example.exception.UserAlreadyInsertedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDaoDB implements GenericDao<Receptionist> {
    private final Connection connection;

    public ReceptionistDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Receptionist receptionist) throws SQLException {
        String sql = "INSERT INTO Receptionist (first_name, last_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?)";
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
    public Receptionist read(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Devi fornire un solo parametro di tipo String (email).");
        }
        String email = (String) keys[0];

        String sql = "SELECT first_name, last_name, email, phone_number, password FROM Receptionist WHERE email = ?";
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
        return null;
    }

    @Override
    public void update(Receptionist receptionist) throws SQLException {
        String sql = "UPDATE Receptionist SET first_name = ?, last_name = ?, phone_number = ?, password = ? WHERE email = ?";
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
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String)) {
            throw new IllegalArgumentException("Devi fornire un solo parametro di tipo String (email).");
        }
        String email = (String) keys[0];

        String sql = "DELETE FROM Receptionist WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Receptionist> readAll() {
        List<Receptionist> receptionists = new ArrayList<>();
        String sql = "SELECT first_name, last_name, email, phone_number, password FROM Receptionist";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                receptionists.add(new Receptionist(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            throw new UserAlreadyInsertedException("Lista non recuperabile, ", e);
        }
        return receptionists;
    }
}
