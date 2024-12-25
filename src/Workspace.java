import java.io.Serializable;

public class Workspace implements Serializable {
    private int id;
    private String type;
    private double price;
    private boolean isAvailable;

    public Workspace(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Price: " + price + ", Available: " + isAvailable;
    }
}
