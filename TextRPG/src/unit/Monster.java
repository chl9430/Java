package unit;

import common.Dice;
import item.Item;

import java.util.Optional;

/**
 * 몬스터: Unit을 상속하고 경험치와 드롭만 추가
 * (생성은 world.MonsterFactory가 맡는다)
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

    /** 확률에 따라 아이템을 떨어뜨린다. 없으면 Optional.empty() */
    public Optional<Item> dropItem(Dice dice) {
        if (drop != null && dice.nextInt(100) < dropRate) {
            return Optional.of(drop);
        }
        return Optional.empty();
    }
}
