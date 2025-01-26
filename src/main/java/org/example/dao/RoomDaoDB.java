package org.example.dao;

import org.example.entity.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoDB implements GenericDao<Room> {
    private final Connection connection;

    public RoomDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Room room) {
        if (read(room.getRoomNumber()) != null) {
            System.out.println("La stanza con il numero " + room.getRoomNumber() + " è già presente.");
            return;
        }

        String insertQuery = "INSERT INTO rooms (room_number, max_people, floor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setInt(1, room.getRoomNumber());
            stmt.setInt(2, room.getMaxPeople());
            stmt.setInt(3, room.getFloor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Room read(Object... keys) {
        if (keys.length != 1) return null;
        int roomNumber = (int) keys[0];

        String query = "SELECT room_number, max_people, floor FROM rooms WHERE room_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getInt("room_number"),
                        rs.getInt("max_people"),
                        rs.getInt("floor")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Room room) {
        String updateQuery = "UPDATE rooms SET max_people = ?, floor = ? WHERE room_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setInt(1, room.getMaxPeople());
            stmt.setInt(2, room.getFloor());
            stmt.setInt(3, room.getRoomNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 1) return;
        int roomNumber = (int) keys[0];

        String deleteQuery = "DELETE FROM rooms WHERE room_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, roomNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> readAll() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT room_number, max_people, floor FROM rooms";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("room_number"),
                        rs.getInt("max_people"),
                        rs.getInt("floor")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
