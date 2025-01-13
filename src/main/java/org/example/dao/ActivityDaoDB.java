package org.example.dao;

import org.example.entity.Activity;
import org.example.exception.DatabaseConfigurationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDaoDB implements GenericDao<Activity> {
    private final Connection connection;

    public ActivityDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Activity entity) throws SQLException {
        String query = "INSERT INTO Activity (name, description, max_participants) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getMaxParticipants());
            statement.executeUpdate();
        }
    }

    @Override
    public Activity read(String name) throws SQLException {
        String query = "SELECT name, description, max_participants FROM Activity WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Activity(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("max_participants")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void update(Activity entity) throws SQLException {
        String query = "UPDATE Activity SET description = ?, max_participants = ? WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getMaxParticipants());
            statement.setString(3, entity.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(String name) throws SQLException {
        String query = "DELETE FROM Activity WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    // Nuovo metodo readAll
    public List<Activity> readAll() {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT name, description, max_participants FROM Activity";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                activities.add(new Activity(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("max_participants")
                ));
            }
        }catch (SQLException e) {
            throw new DatabaseConfigurationException("Lista non recuperabile, ", e);
        }
        return activities;
    }
}
