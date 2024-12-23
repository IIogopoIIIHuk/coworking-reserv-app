import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static final ArrayList<Workspace> workspaces = new ArrayList<>();
    private static final ArrayList<Reservation> reservations = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int reservationIdCounter = 1;

    public static void main(String[] args) {
        AdminService adminService = new AdminService(workspaces, reservations);
        UserService userService = new UserService(workspaces, reservations, reservationIdCounter);

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1:
                    adminMenu(adminService);
                    break;
                case 2:
                    userMenu(userService);
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
        String menu = """
                Welcome to Coworking Space Reservation System
                1. Admin Login
                2. User Login
                3. Exit
                """;
        System.out.println(menu);
    }

    private static int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void adminMenu(AdminService adminService) {
        String adminMenu = """
                Admin Menu
                1. Add a new coworking space
                2. Remove a coworking space
                3. View all reservations
                4. Back to Main Menu
                Choose an option
                """;
        System.out.println(adminMenu);

        int choice = getUserInput();

        switch (choice) {
            case 1:
                adminService.addWorkspace(scanner);
                break;
            case 2:
                adminService.removeWorkspace(scanner);
                break;
            case 3:
                adminService.viewAllReservations();
                break;
            case 4:
                System.out.println("Returning to Main Menu...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private static void userMenu(UserService userService) {
        String userMenu = """
                Customer Menu
                1. Browse available spaces
                2. Make a reservation
                3. View ny reservation
                4. Cancel a reservation
                5. Back to Main Menu
                """;
        System.out.println(userMenu);

        int choice = getUserInput();

        switch (choice) {
            case 1:
                userService.browseSpaces();
                break;
            case 2:
                userService.makeReservation(scanner);
                break;
            case 3:
                userService.viewUserReservations(scanner);
                break;
            case 4:
                userService.cancelReservation(scanner);
                break;
            case 5:
                System.out.println("Returning to Main Menu...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }
}