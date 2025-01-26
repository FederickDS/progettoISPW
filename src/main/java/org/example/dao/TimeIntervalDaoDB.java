package org.example.dao;

import org.example.entity.TimeInterval;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeIntervalDaoDB implements GenericDao<TimeInterval> {
    private final Connection connection;

    public TimeIntervalDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(TimeInterval entity) {
        if (read(entity.getStartDate(), entity.getEndDate(), entity.getType()) != null) {
            return;
        }

        String insertQuery = "INSERT INTO time_intervals (start_date, end_date, type) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setDate(1, Date.valueOf(entity.getStartDate()));
            insertStmt.setDate(2, Date.valueOf(entity.getEndDate()));
            insertStmt.setString(3, entity.getType());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TimeInterval read(Object... keys) {
        if (keys.length != 3) return null;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        String query = "SELECT start_date, end_date, type FROM time_intervals WHERE start_date = ? AND end_date = ? AND type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            stmt.setString(3, type);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TimeInterval(
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(TimeInterval entity) {
        delete(entity.getStartDate(), entity.getEndDate(), entity.getType());
        create(entity);
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 3) return;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        String query = "DELETE FROM time_intervals WHERE start_date = ? AND end_date = ? AND type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            stmt.setString(3, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeInterval> readAll() {
        List<TimeInterval> intervals = new ArrayList<>();
        String query = "SELECT start_date, end_date, type FROM time_intervals";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                intervals.add(new TimeInterval(
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intervals;
    }
}
