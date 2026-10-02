import java.util.Random;

public class Monster {
    String name;
    Element element;
    int maxHp;
    int hp;
    int atk;
    int def;
    int exp;
    Item drop;       // 떨어뜨리는 아이템
    int dropRate;    // 드롭 확률(%)

    Monster(String name, Element element, int hp,
            int atk, int def, int exp) {
        this.name = name;
        this.element = element;
        this.maxHp = hp;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.exp = exp;
    }

    Monster(String name, Element element, int hp, int atk, int def,
            int exp, Item drop, int dropRate) {
        this(name, element, hp, atk, def, exp);
        this.drop = drop;
        this.dropRate = dropRate;
    }

    boolean isAlive() {
        return hp > 0;
    }

    void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    Item dropItem(Random random) {
        if (drop == null) return null;
        if (random.nextInt(100) < dropRate) return drop;
        return null;
    }

    static Monster spawn(int heroLevel, Random random) {
        int range = Math.min(heroLevel + 1, 4);
        return switch (random.nextInt(range)) {
            case 0 -> new Monster("슬라임", Element.WATER, 30, 7, 1, 12,
                    new Item("빨간 포션", ItemType.POTION, 40), 60);
            case 1 -> new Monster("고블린", Element.GRASS, 45, 10, 2, 20,
                    new Item("목검", ItemType.WEAPON, 3), 50);
            case 2 -> new Monster("불도마뱀", Element.FIRE, 55, 13, 3, 28,
                    new Item("가죽옷", ItemType.ARMOR, 3), 50);
            default -> new Monster("오크", Element.GRASS, 80, 17, 5, 45,
                    new Item("철검", ItemType.WEAPON, 7), 40);
        };
    }
}
