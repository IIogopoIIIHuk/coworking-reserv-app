import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {
    private FileService fileService;
    private List<Workspace> workspaces;
    private ArrayList<Reservation> reservations;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        workspaces = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    @Test
    void givenWorkspacesAndReservations_whenSaveAndLoadApplicationState_thenStateIsPersisted() {
        Workspace workspace = new Workspace(1, "Office", 200.0, true);
        Reservation reservation = new Reservation(1, 1, "John", "2025-01-15", "10:00-11:00");
        workspaces.add(workspace);
        reservations.add(reservation);

        fileService.saveApplicationState(workspaces, reservations);

        List<Workspace> loadedWorkspaces = new ArrayList<>();
        ArrayList<Reservation> loadedReservations = new ArrayList<>();
        fileService.loadApplicationState(loadedWorkspaces, loadedReservations);

        assertEquals(1, loadedWorkspaces.size());
        assertEquals(1, loadedReservations.size());
    }
}
