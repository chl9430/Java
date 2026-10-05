package world;

import common.Dice;
import item.Item;
import item.ItemType;
import unit.Element;
import unit.Monster;

import java.util.List;

/**
 * 몬스터 생성 담당 (Factory). 3일차에는 Monster 안의 static 메서드였다.
 * 호출하는 쪽은 "레벨에 맞는 몬스터 줘"만 말한다.
 */
public class MonsterFactory {

    /** 몬스터 설계도 (record) */
    private record Template(int minLevel, String name, Element element, int hp,
                            int atk, int def, int exp, Item drop, int dropRate) {
        Monster create() {
            return new Monster(name, element, hp, atk, def, exp, drop, dropRate);
        }
    }

    private static final List<Template> TEMPLATES = List.of(
            new Template(1, "슬라임", Element.WATER, 30, 7, 1, 12,
                    new Item("빨간 포션", ItemType.POTION, 40), 60),
            new Template(1, "고블린", Element.GRASS, 45, 10, 2, 20,
                    new Item("목검", ItemType.WEAPON, 3), 50),
            new Template(2, "불도마뱀", Element.FIRE, 55, 13, 3, 28,
                    new Item("파란 포션", ItemType.MANA_POTION, 20), 50),
            new Template(3, "오크", Element.GRASS, 80, 17, 5, 45,
                    new Item("가죽옷", ItemType.ARMOR, 3), 40));

    private final Dice dice;

    public MonsterFactory(Dice dice) {
        this.dice = dice;
    }

    /** 영웅 레벨에 맞는 일반 몬스터 (Stream으로 후보를 거른다) */
    public Monster spawn(int heroLevel) {
        List<Template> pool = TEMPLATES.stream()
                .filter(t -> t.minLevel() <= heroLevel)
                .toList();
        return pool.get(dice.nextInt(pool.size())).create();
    }

    /** 던전 마지막 방의 보스 */
    public Monster boss() {
        return new Monster("오크 대장", Element.FIRE, 160, 20, 6, 120,
                new Item("철검", ItemType.WEAPON, 7), 100);
    }
}
