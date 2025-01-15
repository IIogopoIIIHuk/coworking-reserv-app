import java.util.ArrayList;
import java.util.Scanner;

public class AdminService {
    private final CustomList<Workspace> workspaces;
    private final ArrayList<Reservation> reservations;

    public AdminService(CustomList<Workspace> workspaces, ArrayList<Reservation> reservations) {
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

        System.out.println("Current workspaces:");
        for (Workspace workspace : workspaces.toArrayList()) {
            System.out.println(workspace);
        }
    }

    public void removeWorkspace(Scanner scanner) {
        System.out.print("Enter Workspace ID to remove: ");
        int id = scanner.nextInt();
        Workspace toRemove = null;

        for (int i = 0; i < workspaces.size(); i++){
            if (workspaces.get(i).getId() == id){
                toRemove = workspaces.get(i);
                break;
            }
        }
        if (toRemove != null){
            workspaces.remove(toRemove);
            System.out.println("Workspace removed successfully");
        }else {
            System.out.println("Workspace ID not found");
        }
    }

    public void viewAllReservations() {
        System.out.println("\nAll Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
