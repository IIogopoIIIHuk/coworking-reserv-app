import java.util.ArrayList;
import java.util.Scanner;

public class AdminService {
    private final ArrayList<Workspace> workspaces;
    private final ArrayList<Reservation> reservations;

    public AdminService(ArrayList<Workspace> workspaces, ArrayList<Reservation> reservations) {
        this.workspaces = workspaces;
        this.reservations = reservations;
    }

    public void addWorkspace(Scanner scanner) {
        System.out.print("Enter Workspace ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter Workspace Type: ");
        scanner.nextLine();
        String type = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        workspaces.add(new Workspace(id, type, price));
        System.out.println("Workspace added successfully!");
    }

    public void removeWorkspace(Scanner scanner) {
        System.out.print("Enter Workspace ID to remove: ");
        int id = scanner.nextInt();
        workspaces.removeIf(workspace -> workspace.getId() == id);
        System.out.println("Workspace removed successfully!");
    }

    public void viewAllReservations() {
        System.out.println("\nAll Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
