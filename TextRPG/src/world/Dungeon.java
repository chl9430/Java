package world;

import battle.Battle;
import item.ItemType;
import unit.Hero;
import unit.Monster;

import java.util.Scanner;

/**
 * 던전: 방을 하나씩 지나며 반복 전투. 마지막 방에는 보스가 있다.
 * 필요한 부품은 전부 생성자로 받는다 (DI) - new 가 하나도 없다.
 */
public class Dungeon implements Place {
    private final String name;
    private final int rooms;
    private final Battle battle;
    private final MonsterFactory monsters;
    private final Place bag;
    private final Scanner sc;

    public Dungeon(String name, int rooms, Battle battle,
                   MonsterFactory monsters, Place bag, Scanner sc) {
        this.name = name;
        this.rooms = rooms;
        this.battle = battle;
        this.monsters = monsters;
        this.bag = bag;
        this.sc = sc;
    }

    @Override
    public void enter(Hero hero) {
        System.out.println("\n===== " + name + " 입장 =====");
        int room = 1;
        while (room <= rooms) {
            System.out.printf("%n[%s %d/%d] HP %d/%d MP %d 포션 %d개%n",
                    name, room, rooms, hero.getHp(), hero.getMaxHp(),
                    hero.getMp(), hero.getInventory().count(ItemType.POTION));
            System.out.print("1.전진  2.인벤토리  3.마을로 귀환> ");
            String input = sc.nextLine().trim();
            if (input.equals("3")) {
                System.out.println("던전에서 나왔다.");
                return;
            }
            if (input.equals("2")) {
                bag.enter(hero);
                continue;
            }

            boolean bossRoom = (room == rooms);
            Monster m = bossRoom ? monsters.boss() : monsters.spawn(hero.getLevel());
            if (bossRoom) System.out.println("!!! 보스의 방 !!!");

            if (!battle.fight(hero, m, sc)) {
                if (hero.isAlive()) System.out.println("마을로 도망쳤다.");
                return;                 // 도망 or 패배 → 던전 종료
            }
            battle.reward(hero, m);
            room++;
        }
        System.out.println("\n★ " + name + " 클리어! 보너스 경험치 +50 ★");
        hero.gainExp(50);
    }
}
