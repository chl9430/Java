import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 인벤토리: 가득 차면 예외를 던진다.
 */
public class Inventory {
    public static final int MAX_SIZE = 10;

    private final List<Item> items = new ArrayList<>();

    public void add(Item item) {
        if (items.size() >= MAX_SIZE) {
            throw new GameException("가방이 가득 찼습니다.");
        }
        items.add(item);
    }

    public Item get(int index) { return items.get(index); }
    public Item remove(int index) { return items.remove(index); }
    public int size() { return items.size(); }
    public boolean isEmpty() { return items.isEmpty(); }

    /** Item의 compareTo 기준으로 정렬 */
    public void sort() {
        Collections.sort(items);
    }

    public void print() {
        System.out.println("===== 인벤토리 ("
                + items.size() + "/" + MAX_SIZE + ") =====");
        if (items.isEmpty()) System.out.println("(비어 있음)");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }
}
