import lombok.extern.slf4j.Slf4j;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileService extends Exception{


    public void saveApplicationState(List<Workspace> workspaces, ArrayList<Reservation> reservations){
        try (ObjectOutputStream workspaceOut = new ObjectOutputStream(new FileOutputStream("workspaces.bin"));
             ObjectOutputStream reservationOut = new ObjectOutputStream(new FileOutputStream("reservations.bin"))){

            workspaceOut.writeObject(workspaces);
            reservationOut.writeObject(reservations);
            log.info("Application state saved");
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
