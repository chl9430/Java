import java.util.Random;
import java.util.Scanner;

/**
 * [4단계] 던전: 방을 하나씩 지나며 몬스터와 반복 전투
 * 마지막 방에는 보스가 있다.
 */
public class Dungeon {
    private final String name;
    private final int rooms;

    public Dungeon(String name, int rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public void explore(Hero hero, Scanner sc, Random random) {
        System.out.println("\n===== " + name + " 입장 =====");
        int room = 1;
        while (room <= rooms) {
            System.out.printf("%n[%s %d/%d] HP %d/%d MP %d%n",
                    name, room, rooms,
                    hero.getHp(), hero.getMaxHp(), hero.getMp());
            System.out.print("1.전진  2.인벤토리  3.마을로 귀환> ");
            String input = sc.nextLine().trim();
            if (input.equals("3")) {
                System.out.println("던전에서 나왔다.");
                return;
            }
            if (input.equals("2")) {
                Main.openInventory(hero, sc);
                continue;
            }

            boolean bossRoom = (room == rooms);
            Monster m = bossRoom ? Monster.boss()
                                 : Monster.spawn(hero.getLevel(), random);
            if (bossRoom) System.out.println("!!! 보스의 방 !!!");

            if (!Battle.fight(hero, m, sc, random)) {
                if (hero.isAlive()) System.out.println("마을로 도망쳤다.");
                return;                 // 도망 or 패배 → 던전 종료
            }
            Battle.reward(hero, m, random);
            room++;
        }
        System.out.println("\n★ " + name + " 클리어! 보너스 경험치 +50 ★");
        hero.gainExp(50);
    }
}
