import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTest {
    private AdminService adminService;
    private CustomList<Workspace> workspaces;
    private ArrayList<Reservation> reservations;


    @BeforeEach
    void setUp(){
        workspaces = new CustomList<>();
        reservations = new ArrayList<>();
        adminService = new AdminService(workspaces, reservations);
    }

    @Test
    void givenValidInput_whenAddWorkspace_thenWorkspaceIsAdded() {
        Scanner scanner = new Scanner("1\nOffice\n200\n");

        adminService.addWorkspace(scanner);

        assertEquals(1, workspaces.size());
        assertEquals("Office", workspaces.get(0).getType());
        assertEquals(200.0, workspaces.get(0).getPrice());
    }

    @Test
    void givenValidWorkspace_whenRemoveWorkspace_thenWorkspaceIsRemoved() {
        Workspace workspace = new Workspace(1, "Office", 200.0);
        workspaces.add(workspace);

        Scanner scanner = new Scanner("1\n");
        adminService.removeWorkspace(scanner);

        assertEquals(0, workspaces.size());
    }

    @Test
    void givenReservations_whenViewAllReservations_thenReservationsAreDisplayed() {
        Reservation reservation = new Reservation(1, 1, "John", "2025-01-15", "10:00-11:00");
        reservations.add(reservation);

        assertDoesNotThrow(() -> adminService.viewAllReservations());
    }
}
