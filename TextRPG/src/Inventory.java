import java.util.ArrayList;

public class Inventory {
    static final int MAX_SIZE = 10;

    ArrayList<Item> items = new ArrayList<>();

    boolean add(Item item) {
        if (items.size() >= MAX_SIZE) return false;
        items.add(item);
        return true;
    }

    Item remove(int index) {
        return items.remove(index);
    }

    Item get(int index) {
        return items.get(index);
    }

    int size() {
        return items.size();
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    void print() {
        System.out.println("===== 인벤토리 ("
                + items.size() + "/" + MAX_SIZE + ") =====");
        if (items.isEmpty()) System.out.println("(비어 있음)");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }
}
