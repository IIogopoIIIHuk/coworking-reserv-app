import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Reservation implements Serializable {

    private int id;
    private int workspaceId;
    private String customerName;
    private String date;
    private String time;

    @Override
    public String toString() {
        return "Reservation ID: " + id + ", Workspace ID: " + workspaceId + ", Customer: " + customerName + ", Date: " + date + ", Time: " + time;
    }
}
