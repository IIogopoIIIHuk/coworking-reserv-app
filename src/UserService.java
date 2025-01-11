import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

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
        workspaces.toArrayList()
                .stream()
                .forEach(System.out::println);
    }

    public void makeReservation(Scanner scanner) throws InvalidReservationException{
        System.out.print("Enter Workspace ID to reserve: ");
        int workspaceId = scanner.nextInt();

        Optional<Workspace> workspace = findWorkspaceById(workspaceId);

        Workspace availableWorkspace = workspace
                .filter(Workspace::isAvailable)
                .orElseThrow(() -> new InvalidReservationException("Invalid Workspace ID or Workspace not available."));

        System.out.print("Enter your name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter time (HH:MM-HH:MM): ");
        String time = scanner.nextLine();

        reservations.add(new Reservation(reservationIdCounter++, workspaceId, name, date, time));
        availableWorkspace.setAvailable(false);
        System.out.println("Reservation successful!");
    }

    public void viewUserReservations(Scanner scanner) {
        System.out.print("Enter your name to view reservations: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        reservations.stream()
                        .filter(reservation -> reservation.getCustomerName().equals(name))
                        .forEach(System.out::println);
    }

    public void cancelReservation(Scanner scanner) {
        System.out.print("Enter Reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        Optional<Reservation> reservation = findReservationById(reservationId);

        reservation.ifPresentOrElse(res -> {
            reservations.remove(res);
            findWorkspaceById(res.getWorkspaceId()).ifPresent(ws -> ws.setAvailable(true));
            System.out.println("Reservation canceled successfully!");
        }, () -> System.out.println("Invalid Reservation ID."));
    }
    private Optional<Workspace> findWorkspaceById(int id) {
        return workspaces.toArrayList()
                .stream()
                .filter(workspace -> workspace.getId() == id)
                .findFirst();
    }

    private Optional<Reservation> findReservationById(int id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst();
    }
}
