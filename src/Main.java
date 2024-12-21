import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static final ArrayList<Workspace> workspaces = new ArrayList<>();
    private static final ArrayList<Reservation> reservations = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int reservationIdCounter = 1;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\nWelcome to Coworking Space Reservation System");
        System.out.println("1. Admin Login");
        System.out.println("2. User Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void adminMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("1. Add a new coworking space");
        System.out.println("2. Remove a coworking space");
        System.out.println("3. View all reservations");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        int choice = getUserInput();

        switch (choice) {
            case 1:
                addWorkspace();
                break;
            case 2:
                removeWorkspace();
                break;
            case 3:
                viewAllReservations();
                break;
            case 4:
                System.out.println("Returning to Main Menu...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void userMenu() {
        System.out.println("\nCustomer Menu");
        System.out.println("1. Browse available spaces");
        System.out.println("2. Make a reservation");
        System.out.println("3. View my reservations");
        System.out.println("4. Cancel a reservation");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose an option: ");

        int choice = getUserInput();

        switch (choice) {
            case 1:
                browseSpaces();
                break;
            case 2:
                makeReservation();
                break;
            case 3:
                viewUserReservations();
                break;
            case 4:
                cancelReservation();
                break;
            case 5:
                System.out.println("Returning to Main Menu...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void addWorkspace() {
        System.out.print("Enter Workspace ID: ");
        int id = getUserInput();

        System.out.print("Enter Workspace Type: ");
        scanner.nextLine();
        String type = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        workspaces.add(new Workspace(id, type, price));
        System.out.println("Workspace added successfully!");
    }

    private static void removeWorkspace() {
        System.out.print("Enter Workspace ID to remove: ");
        int id = getUserInput();
        workspaces.removeIf(workspace -> workspace.getId() == id);
        System.out.println("Workspace removed successfully!");
    }

    private static void viewAllReservations() {
        System.out.println("\nAll Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private static void browseSpaces() {
        System.out.println("\nAvailable Workspaces:");
        for (Workspace workspace : workspaces) {
            System.out.println(workspace);
        }
    }

    private static void makeReservation() {
        System.out.print("Enter Workspace ID to reserve: ");
        int workspaceId = getUserInput();

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



    private static void viewUserReservations() {
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

    private static void cancelReservation() {
        System.out.print("Enter Reservation ID to cancel: ");
        int reservationId = getUserInput();

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

    private static Workspace findWorkspaceById(int id) {
        for (Workspace workspace : workspaces) {
            if (workspace.getId() == id) {
                return workspace;
            }
        }
        return null;
    }

    private static Reservation findReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }
}