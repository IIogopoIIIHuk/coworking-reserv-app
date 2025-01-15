import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workspace implements Serializable {
    private int id;
    private String type;
    private double price;
    private boolean isAvailable;

    public Workspace(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }
}
