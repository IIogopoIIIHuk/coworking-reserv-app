import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class AdminService {
    private final CustomList<Workspace> workspaces;
    private final ArrayList<Reservation> reservations;

    public AdminService(CustomList<Workspace> workspaces, ArrayList<Reservation> reservations) {
        this.workspaces = workspaces;
        this.reservations = reservations;
    }

    public void addWorkspace(Scanner scanner) {
        System.out.print("Enter Workspace Type: ");
        scanner.nextLine();
        String type = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        String query = "INSERT INTO workspaces (type, price) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, type);
            statement.setDouble(2, price);

            statement.executeUpdate();
            System.out.println("Workspace added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding workspace: " + e.getMessage());
        }
    }

    public void removeWorkspace(Scanner scanner) {
        System.out.print("Enter Workspace ID to remove: ");
        int id = scanner.nextInt();

        String qeury = "DELETE FROM workspaces WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(qeury)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Workspace removed successfully!");
            } else {
                System.out.println("Workspace ID not found.");
            }
        }catch (SQLException e){
            System.err.println("Error removing workspace: " + e.getMessage());
        }
    }

    public void viewAllReservations() {
        System.out.println("\nAll Reservations:");

        String query = "SELECT * FROM reservations";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nAll Reservations:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int workspaceId = resultSet.getInt("workspace_id");
                String customerName = resultSet.getString("customer_name");
                String date = resultSet.getString("reservation_date");
                String time = resultSet.getString("reservation_time");

                System.out.printf("ID: %d, Workspace ID: %d, Customer: %s, Date: %s, Time: %s%n",
                        id, workspaceId, customerName, date, time);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving reservations: " + e.getMessage());
        }
    }
}
