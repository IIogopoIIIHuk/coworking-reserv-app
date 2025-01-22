import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.SortedMap;

public class UserService {
    private final CustomList<Workspace> workspaces;
    private final ArrayList<Reservation> reservations;
    private int reservationIdCounter;

    public UserService(CustomList<Workspace> workspaces, ArrayList<Reservation> reservations, int reservationIdCounter) {
        this.workspaces = workspaces;
        this.reservations = reservations;
        this.reservationIdCounter = reservationIdCounter;
    }

    public void browseSpaces() {
        String query = "SELECT * FROM workspaces WHERE is_available = true";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                boolean isAvailable = resultSet.getBoolean("is_available");

                System.out.printf("ID: %d, Type: %s, Price: %.2f, Available: %s%n",
                        id, type, price, isAvailable);
            }
        } catch (SQLException e) {
            System.err.println("Error browsing spaces: " + e.getMessage());
        }

    }

    public void makeReservation(Scanner scanner) throws InvalidReservationException {
        System.out.print("Enter Workspace ID to reserve: ");
        int workspaceId = scanner.nextInt();

        String workspaceQuery = "SELECT * FROM workspaces WHERE id = ? AND is_available = true";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement workspaceStmt = connection.prepareStatement(workspaceQuery)) {

            workspaceStmt.setInt(1, workspaceId);
            ResultSet workspaceResult = workspaceStmt.executeQuery();

            if (!workspaceResult.next()) {
                throw new InvalidReservationException("Invalid Workspace ID or Workspace not available.");
            }

            System.out.print("Enter your name: ");
            scanner.nextLine(); // Consume newline
            String name = scanner.nextLine();

            System.out.print("Enter date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            java.sql.Date reservationDate = java.sql.Date.valueOf(dateInput); // Преобразование строки в тип java.sql.Date

            System.out.print("Enter time (HH:MM-HH:MM): ");
            String time = scanner.nextLine();

            String reservationQuery = "INSERT INTO reservations (workspace_id, customer_name, reservation_date, reservation_time) VALUES (?, ?, ?, ?)";
            try (PreparedStatement reservationStmt = connection.prepareStatement(reservationQuery)) {
                reservationStmt.setInt(1, workspaceId);
                reservationStmt.setString(2, name);
                reservationStmt.setDate(3, reservationDate); // Установка значения как java.sql.Date
                reservationStmt.setString(4, time);

                reservationStmt.executeUpdate();

                String updateWorkspaceQuery = "UPDATE workspaces SET is_available = false WHERE id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateWorkspaceQuery)) {
                    updateStmt.setInt(1, workspaceId);
                    updateStmt.executeUpdate();
                }

                System.out.println("Reservation successful!");
            }
        } catch (SQLException e) {
            System.err.println("Error making reservation: " + e.getMessage());
        }
    }



    public void viewUserReservations(Scanner scanner) {
        System.out.print("Enter your name to view reservations: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        String query = "SELECT*FROM reservations WHERE customer_name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\nYour Reservations:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int workspaceId = resultSet.getInt("workspace_id");
                String date = resultSet.getString("reservation_date");
                String time = resultSet.getString("reservation_time");

                System.out.printf("ID: %d, Workspace ID: %d, Date: %s, Time: %s%n", id, workspaceId, date, time);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving your reservations: " + e.getMessage());
        }
    }

    public void cancelReservation(Scanner scanner) {
        System.out.print("Enter Reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        String reservationQuery = "SELECT * FROM reservations WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement reservationStmt = connection.prepareStatement(reservationQuery)) {

            reservationStmt.setInt(1, reservationId);
            ResultSet reservationResult = reservationStmt.executeQuery();

            if (reservationResult.next()) {
                int workspaceId = reservationResult.getInt("workspace_id");

                String deleteReservationQuery = "DELETE FROM reservations WHERE id = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteReservationQuery)) {
                    deleteStmt.setInt(1, reservationId);
                    deleteStmt.executeUpdate();
                }

                String updateWorkspaceQuery = "UPDATE workspaces SET is_available = true WHERE id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateWorkspaceQuery)) {
                    updateStmt.setInt(1, workspaceId);
                    updateStmt.executeUpdate();
                }

                System.out.println("Reservation canceled successfully!");
            } else {
                System.out.println("Invalid Reservation ID.");
            }
        } catch (SQLException e){
            System.err.println("Error canceling reservation: " + e.getMessage());
        }

    }

    private Optional<Workspace> findWorkspaceById(int id) {
        String query = "SELECT * FROM workspaces WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int workspaceId = resultSet.getInt("id");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                boolean isAvailable = resultSet.getBoolean("is_available");

                Workspace workspace = new Workspace(workspaceId, type, price, isAvailable);
                return Optional.of(workspace);
            }
        } catch (SQLException e) {
            System.err.println("Error finding workspace by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    private Optional<Reservation> findReservationById(int id) {
        String query = "SELECT * FROM reservations WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int reservationId = resultSet.getInt("id");
                int workspaceId = resultSet.getInt("workspace_id");
                String customerName = resultSet.getString("customer_name");
                String reservationDate = resultSet.getString("reservation_date");
                String reservationTime = resultSet.getString("reservation_time");

                Reservation reservation = new Reservation(
                        reservationId,
                        workspaceId,
                        customerName,
                        reservationDate,
                        reservationTime
                );

                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            System.err.println("Error finding reservation by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

}
