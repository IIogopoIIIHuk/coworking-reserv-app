import java.util.ArrayList;

public class CustomList<T> {

    private final ArrayList<T> items = new ArrayList<>();

    public void add(T item){
        items.add(item);
    }

    public void remove(T item){
        items.remove(item);
    }

    public T get(int index){
        return items.get(index);
    }

    public int size(){
        return items.size();
    }

    public ArrayList<T> toArrayList() {
        return new ArrayList<>(items);
    }

    @Override
    public String toString(){
        return items.toString();
    }
}
