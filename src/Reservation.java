import java.io.Serializable;

public class Reservation implements Serializable {

    private int id;
    private int workspaceId;
    private String customerName;
    private String date;
    private String time;

    public Reservation(int id, int workspaceId, String customerName, String date, String time) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.customerName = customerName;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + id + ", Workspace ID: " + workspaceId + ", Customer: " + customerName + ", Date: " + date + ", Time: " + time;
    }
}
