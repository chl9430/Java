public class Item {
    String name;
    ItemType type;
    int power;

    Item(String name, ItemType type, int power) {
        this.name = name;
        this.type = type;
        this.power = power;
    }

    @Override
    public String toString() {
        String stat = switch (type) {
            case WEAPON -> "공격+" + power;
            case ARMOR -> "방어+" + power;
            case POTION -> "회복+" + power;
        };
        return name + "(" + stat + ")";
    }
}
