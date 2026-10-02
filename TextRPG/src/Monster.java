import java.util.Random;

/**
 * 몬스터: Unit을 상속하고 경험치와 드롭만 추가
 */
public class Monster extends Unit {
    private final int exp;
    private final Item drop;
    private final int dropRate;

    public Monster(String name, Element element, int hp, int atk,
                   int def, int exp, Item drop, int dropRate) {
        super(name, element, hp, atk, def);
        this.exp = exp;
        this.drop = drop;
        this.dropRate = dropRate;
    }

    public int getExp() { return exp; }

    /** 확률에 따라 아이템을 떨어뜨린다. 없으면 null */
    public Item dropItem(Random random) {
        if (drop == null) return null;
        if (random.nextInt(100) < dropRate) return drop;
        return null;
    }

    /** 레벨에 맞는 일반 몬스터 (매번 new) */
    public static Monster spawn(int heroLevel, Random random) {
        int range = Math.min(heroLevel + 1, 4);
        return switch (random.nextInt(range)) {
            case 0 -> new Monster("슬라임", Element.WATER, 30, 7, 1, 12,
                    new Item("빨간 포션", ItemType.POTION, 40), 60);
            case 1 -> new Monster("고블린", Element.GRASS, 45, 10, 2, 20,
                    new Item("목검", ItemType.WEAPON, 3), 50);
            case 2 -> new Monster("불도마뱀", Element.FIRE, 55, 13, 3, 28,
                    new Item("파란 포션", ItemType.MANA_POTION, 20), 50);
            default -> new Monster("오크", Element.GRASS, 80, 17, 5, 45,
                    new Item("가죽옷", ItemType.ARMOR, 3), 40);
        };
    }

    /** 던전 마지막 방의 보스 */
    public static Monster boss() {
        return new Monster("오크 대장", Element.FIRE, 160, 20, 6, 120,
                new Item("철검", ItemType.WEAPON, 7), 100);
    }
}
