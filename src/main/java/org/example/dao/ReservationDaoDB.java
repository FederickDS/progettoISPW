package org.example.dao;

import org.example.entity.Reservation;
import org.example.entity.Room;
import org.example.entity.DailyTimeInterval;
import org.example.entity.Service;
import org.example.entity.Activity;
import org.example.entity.Client;
import org.example.entity.ReservationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReservationDaoDB implements GenericDao<Reservation> {
    private final Connection connection;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DaoFactoryInterface daoFactoryInterface;
    private static final String RESERVATION_ID = "reservation_id";

    public ReservationDaoDB(Connection connection, DaoFactoryInterface daoFactory) {
        this.daoFactoryInterface = daoFactory;
        this.connection = connection;
    }

    @Override
    public void create(Reservation reservation) {
        String insertReservation = "INSERT INTO Reservation (room_number, timetable_start_date, timetable_end_date, timetable_type, price, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reservation.getRoom().getRoomNumber());
            stmt.setDate(2, Date.valueOf(reservation.getTimetable().getStartDate()));
            stmt.setDate(3, Date.valueOf(reservation.getTimetable().getEndDate()));
            stmt.setString(4, reservation.getTimetable().getType());
            stmt.setBigDecimal(5, reservation.getPrice());
            stmt.setString(6, reservation.getStatus().name());  // Enum -> String

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                handleGeneratedKeys(stmt, reservation);
            } else {
                logger.info("Nessuna riga è stata modificata, verifica i dati inseriti.");
            }
        } catch (SQLException e) {
            logger.severe("Prenotazione fallita: " + e.getMessage());
        }
    }

    private void handleGeneratedKeys(PreparedStatement stmt, Reservation reservation) {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int reservationId = generatedKeys.getInt(1);
                insertRelatedEntities(reservationId, reservation);
            } else {
                logger.info("Nessun ID generato, possibile errore.");
            }
        } catch (SQLException e) {
            logger.severe("Aggiunta elementi prenotazione fallita: " + e.getMessage());
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

        // Imposta l'ID della prenotazione
        reservation.setReservationId(rs.getInt(RESERVATION_ID));
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

        // Mappa prezzo e stato
        reservation.setPrice(rs.getBigDecimal("price"));
        reservation.setStatus(ReservationStatus.valueOf(rs.getString("status")));  // Enum -> String

        // Recupera e mappa i servizi associati alla prenotazione
        List<Service> services = getServicesForReservation(rs.getInt(RESERVATION_ID));
        reservation.setFreeServices(services);

        // Recupera e mappa le attività associate alla prenotazione
        List<Activity> activities = getActivitiesForReservation(rs.getInt(RESERVATION_ID));
        reservation.setFreeActivities(activities);

        // Recupera e mappa i clienti associati alla prenotazione
        List<Client> clients = getClientsForReservation(rs.getInt(RESERVATION_ID));
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
                Service service = daoFactoryInterface.getServiceDao().read(rs.getString("service_name"));
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
                Activity activity = daoFactoryInterface.getActivityDao().read(rs.getString("activity_name"));
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
                Client client = daoFactoryInterface.getClientDao().read(rs.getString("client_email"));
                clients.add(client);
            }
        }
        return clients;
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String updateReservation = "UPDATE Reservation SET room_number = ?, timetable_start_date = ?, timetable_end_date = ?, timetable_type = ?, price = ?, status = ? WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateReservation)) {
            stmt.setInt(1, reservation.getRoom().getRoomNumber());
            stmt.setDate(2, Date.valueOf(reservation.getTimetable().getStartDate()));
            stmt.setDate(3, Date.valueOf(reservation.getTimetable().getEndDate()));
            stmt.setString(4, reservation.getTimetable().getType());
            stmt.setBigDecimal(5, reservation.getPrice());
            stmt.setString(6, reservation.getStatus().name());  // Enum -> String
            stmt.setInt(7, reservation.getReservationId());
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
                reservation.setFreeServices(getServicesForReservation(rs.getInt(RESERVATION_ID)));
                reservation.setFreeActivities(getActivitiesForReservation(rs.getInt(RESERVATION_ID)));
                reservation.setClients(getClientsForReservation(rs.getInt(RESERVATION_ID)));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}