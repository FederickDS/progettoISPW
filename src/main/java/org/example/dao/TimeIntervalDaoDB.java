package org.example.dao;

import org.example.entity.DailyTimeInterval;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeIntervalDaoDB implements GenericDao<DailyTimeInterval> {
    private final Connection connection;

    public TimeIntervalDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(DailyTimeInterval entity) {
        if (read(entity.getStartDate(), entity.getEndDate(), entity.getType()) != null) {
            return;
        }

        String insertQuery = "INSERT INTO DailyTimeInterval (start_date, end_date, type) VALUES (?, ?, ?)";
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
    public DailyTimeInterval read(Object... keys) {
        if (keys.length != 3) return null;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        String query = "SELECT start_date, end_date, type FROM DailyTimeInterval WHERE start_date = ? AND end_date = ? AND type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            stmt.setString(3, type);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DailyTimeInterval(
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
    public void update(DailyTimeInterval entity) {
        delete(entity.getStartDate(), entity.getEndDate(), entity.getType());
        create(entity);
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 3) return;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        String query = "DELETE FROM DailyTimeInterval WHERE start_date = ? AND end_date = ? AND type = ?";
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
    public List<DailyTimeInterval> readAll() {
        List<DailyTimeInterval> intervals = new ArrayList<>();
        String query = "SELECT start_date, end_date, type FROM DailyTimeInterval";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                intervals.add(new DailyTimeInterval(
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
