/**
 * 아이템. 필드는 private, 값은 getter로만 읽는다.
 * Comparable: 종류 순 → 같은 종류면 power 큰 순으로 정렬
 */
public class Item implements Comparable<Item> {
    private final String name;
    private final ItemType type;
    private final int power;

    public Item(String name, ItemType type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    public String getName() { return name; }
    public ItemType getType() { return type; }
    public int getPower() { return power; }

    @Override
    public int compareTo(Item other) {
        if (type != other.type) {
            return type.compareTo(other.type);
        }
        return Integer.compare(other.power, power);
    }

    @Override
    public String toString() {
        String stat = switch (type) {
            case WEAPON -> "공격+" + power;
            case ARMOR -> "방어+" + power;
            case POTION -> "HP+" + power;
            case MANA_POTION -> "MP+" + power;
        };
        return name + "(" + stat + ")";
    }
}
