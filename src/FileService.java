import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService extends Exception{

    public void saveApplicationState(List<Workspace> workspaces, ArrayList<Reservation> reservations){
        try (ObjectOutputStream workspaceOut = new ObjectOutputStream(new FileOutputStream("workspaces.bin"));
            ObjectOutputStream reservationOut = new ObjectOutputStream(new FileOutputStream("reservations.bin"))){

            workspaceOut.writeObject(workspaces);
            reservationOut.writeObject(reservations);
            System.out.println("Application state saved.");
        }catch (IOException e){
            System.out.println("Error saving application state: " + e.getMessage());
        }
    }

    public void loadApplicationState(List<Workspace> workspaces, ArrayList<Reservation> reservations) {
        try (ObjectInputStream workspaceIn = new ObjectInputStream(new FileInputStream("workspaces.bin"));
             ObjectInputStream reservationIn = new ObjectInputStream(new FileInputStream("reservations.bin"))) {

            workspaces.addAll((ArrayList<Workspace>) workspaceIn.readObject());
            reservations.addAll((ArrayList<Reservation>) reservationIn.readObject());
            System.out.println("Application state loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading application state: " + e.getMessage());
        }
    }

}
