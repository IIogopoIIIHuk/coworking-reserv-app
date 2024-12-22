import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private final ArrayList<Workspace> workspaces;
    private final ArrayList<Reservation> reservations;
    private int reservationIdCounter;

    public UserService(ArrayList<Workspace> workspaces, ArrayList<Reservation> reservations, int reservationIdCounter) {
        this.workspaces = workspaces;
        this.reservations = reservations;
        this.reservationIdCounter = reservationIdCounter;
    }

    public void browseSpaces() {
        System.out.println("\nAvailable Workspaces:");
        for (Workspace workspace : workspaces) {
            System.out.println(workspace);
        }
    }

    public void makeReservation(Scanner scanner) {
        System.out.print("Enter Workspace ID to reserve: ");
        int workspaceId = scanner.nextInt();

        Workspace workspace = findWorkspaceById(workspaceId);
        if (workspace == null || !workspace.isAvailable()) {
            System.out.println("Invalid Workspace ID or not available.");
            return;
        }

        System.out.print("Enter your name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter time (HH:MM-HH:MM): ");
        String time = scanner.nextLine();

        reservations.add(new Reservation(reservationIdCounter++, workspaceId, name, date, time));
        workspace.setAvailable(false);
        System.out.println("Reservation successful!");
    }

    public void viewUserReservations(Scanner scanner) {
        System.out.print("Enter your name to view reservations: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.println("\nYour Reservations:");
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name)) {
                System.out.println(reservation);
            }
        }
    }

    public void cancelReservation(Scanner scanner) {
        System.out.print("Enter Reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            System.out.println("Invalid Reservation ID.");
            return;
        }

        reservations.remove(reservation);
        Workspace workspace = findWorkspaceById(reservation.getWorkspaceId());
        if (workspace != null) {
            workspace.setAvailable(true);
        }
        System.out.println("Reservation canceled successfully!");
    }

    private Workspace findWorkspaceById(int id) {
        for (Workspace workspace : workspaces) {
            if (workspace.getId() == id) {
                return workspace;
            }
        }
        return null;
    }

    private Reservation findReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }
}
