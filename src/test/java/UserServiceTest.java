import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    private CustomList<Workspace> workspaces;
    private ArrayList<Reservation> reservations;

    @BeforeEach
    void setUp() {
        workspaces = new CustomList<>();
        reservations = new ArrayList<>();
        userService = new UserService(workspaces, reservations, 1);
    }

    @Test
    void testMakeReservation() throws Exception {
        Workspace workspace = new Workspace(1, "Office", 200.0, true);
        workspaces.add(workspace);

        Scanner scanner = new Scanner("1\nJohn\n2025-01-15\n10:00-11:00\n");
        userService.makeReservation(scanner);

        assertEquals(1, reservations.size());
        assertEquals("John", reservations.get(0).getCustomerName());
        assertFalse(workspaces.get(0).isAvailable());
    }

    @Test
    void testMakeReservationInvalid() {
        Workspace workspace = new Workspace(1, "Office", 200.0, false);
        workspaces.add(workspace);

        Scanner scanner = new Scanner("1\nJohn\n2025-01-15\n10:00-11:00\n");

        Exception exception = assertThrows(InvalidReservationException.class, () -> userService.makeReservation(scanner));
        assertEquals("Invalid Workspace ID or Workspace not available.", exception.getMessage());
    }

    @Test
    void testCancelReservation() {
        Workspace workspace = new Workspace(1, "Office", 200.0, false);
        workspaces.add(workspace);
        Reservation reservation = new Reservation(1, 1, "John", "2025-01-15", "10:00-11:00");
        reservations.add(reservation);

        Scanner scanner = new Scanner("1\n");
        userService.cancelReservation(scanner);

        assertTrue(workspaces.get(0).isAvailable());
        assertEquals(0, reservations.size());
    }
}