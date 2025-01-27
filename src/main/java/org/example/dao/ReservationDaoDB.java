package org.example.dao;

import org.example.entity.Reservation;
import org.example.entity.Room;
import org.example.entity.DailyTimeInterval;
import org.example.entity.Service;
import org.example.entity.Activity;
import org.example.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoDB implements GenericDao<Reservation> {
    private final Connection connection;

    public ReservationDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Reservation reservation) throws SQLException {
        String insertReservation = "INSERT INTO Reservation (room_number, timetable_start_date, timetable_end_date, timetable_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reservation.getRoom().getRoomNumber());
            stmt.setDate(2, Date.valueOf(reservation.getTimetable().getStartDate()));
            stmt.setDate(3, Date.valueOf(reservation.getTimetable().getEndDate()));
            stmt.setString(4, reservation.getTimetable().getType());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int reservationId = generatedKeys.getInt(1);
                        insertRelatedEntities(reservationId, reservation);
                    }
                }
            }
        }
    }

    private void insertRelatedEntities(int reservationId, Reservation reservation) throws SQLException {
        insertServices(reservationId, reservation.getFreeServices());
        insertActivities(reservationId, reservation.getFreeActivities());
        insertClients(reservationId, reservation.getClients());
    }

    private void insertServices(int reservationId, List<Service> services) throws SQLException {
        String insertService = "INSERT INTO ReservationService (reservation_id, service_name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertService)) {
            for (Service service : services) {
                stmt.setInt(1, reservationId);
                stmt.setString(2, service.getName());
                stmt.executeUpdate();
            }
        }
    }

    private void insertActivities(int reservationId, List<Activity> activities) throws SQLException {
        String insertActivity = "INSERT INTO ReservationActivity (reservation_id, activity_name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertActivity)) {
            for (Activity activity : activities) {
                stmt.setInt(1, reservationId);
                stmt.setString(2, activity.getName());
                stmt.executeUpdate();
            }
        }
    }

    private void insertClients(int reservationId, List<Client> clients) throws SQLException {
        String insertClient = "INSERT INTO ReservationClient (reservation_id, client_email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertClient)) {
            for (Client client : clients) {
                stmt.setInt(1, reservationId);
                stmt.setString(2, client.getEmail());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Reservation read(Object... keys) throws SQLException {
        int reservationId = (int) keys[0];  // Assumendo che la chiave primaria sia un id

        String selectReservation = "SELECT * FROM Reservation WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectReservation)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Reservation reservation = mapToReservation(rs);
                reservation.setFreeServices(getServicesForReservation(reservationId));
                reservation.setFreeActivities(getActivitiesForReservation(reservationId));
                reservation.setClients(getClientsForReservation(reservationId));
                return reservation;
            }
        }
        return null;
    }

    private Reservation mapToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();

        // Mappa DailyTimeInterval
        DailyTimeInterval timetable = new DailyTimeInterval(
                rs.getDate("timetable_start_date").toLocalDate(),
                rs.getDate("timetable_end_date").toLocalDate(),
                rs.getString("timetable_type")
        );
        reservation.setTimetable(timetable);

        // Mappa Room
        Room room = new Room();
        room.setRoomNumber(rs.getInt("room_number"));
        reservation.setRoom(room);

        // Recupera e mappa i servizi associati alla prenotazione
        List<Service> services = getServicesForReservation(rs.getInt("reservation_id"));
        reservation.setFreeServices(services);

        // Recupera e mappa le attività associate alla prenotazione
        List<Activity> activities = getActivitiesForReservation(rs.getInt("reservation_id"));
        reservation.setFreeActivities(activities);

        // Recupera e mappa i clienti associati alla prenotazione
        List<Client> clients = getClientsForReservation(rs.getInt("reservation_id"));
        reservation.setClients(clients);

        return reservation;
    }

    private List<Service> getServicesForReservation(int reservationId) throws SQLException {
        List<Service> services = new ArrayList<>();
        String selectServices = "SELECT * FROM ReservationService WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectServices)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = DaoFactory.getServiceDao().read(rs.getInt("service_id"));
                services.add(service);
            }
        }
        return services;
    }

    private List<Activity> getActivitiesForReservation(int reservationId) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String selectActivities = "SELECT * FROM ReservationActivity WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectActivities)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Activity activity = DaoFactory.getActivityDao().read(rs.getInt("activity_id"));
                activities.add(activity);
            }
        }
        return activities;
    }

    private List<Client> getClientsForReservation(int reservationId) throws SQLException {
        List<Client> clients = new ArrayList<>();
        String selectClients = "SELECT * FROM ReservationClient WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectClients)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = DaoFactory.getClientDao().read(rs.getString("client_email"));
                clients.add(client);
            }
        }
        return clients;
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String updateReservation = "UPDATE Reservation SET room_number = ?, timetable_start_date = ?, timetable_end_date = ?, timetable_type = ? WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateReservation)) {
            stmt.setInt(1, reservation.getRoom().getRoomNumber());
            stmt.setDate(2, Date.valueOf(reservation.getTimetable().getStartDate()));
            stmt.setDate(3, Date.valueOf(reservation.getTimetable().getEndDate()));
            stmt.setString(4, reservation.getTimetable().getType());
            stmt.setInt(5, reservation.getReservationId());
            stmt.executeUpdate();
        }

        // Update related entities
        deleteRelatedEntities(reservation.getReservationId());
        insertRelatedEntities(reservation.getReservationId(), reservation);
    }

    private void deleteRelatedEntities(int reservationId) throws SQLException {
        String deleteServices = "DELETE FROM ReservationService WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteServices)) {
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        }

        String deleteActivities = "DELETE FROM ReservationActivity WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteActivities)) {
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        }

        String deleteClients = "DELETE FROM ReservationClient WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteClients)) {
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        int reservationId = (int) keys[0]; // Assumendo che la chiave primaria sia un id

        deleteRelatedEntities(reservationId);

        String deleteReservation = "DELETE FROM Reservation WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteReservation)) {
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Reservation> readAll() {
        List<Reservation> reservations = new ArrayList<>();
        String selectAllReservations = "SELECT * FROM Reservation";
        try (PreparedStatement stmt = connection.prepareStatement(selectAllReservations)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = mapToReservation(rs);
                reservation.setFreeServices(getServicesForReservation(rs.getInt("reservation_id")));
                reservation.setFreeActivities(getActivitiesForReservation(rs.getInt("reservation_id")));
                reservation.setClients(getClientsForReservation(rs.getInt("reservation_id")));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}