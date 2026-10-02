import java.util.Random;
import java.util.Scanner;

/**
 * [3일차] 상속·추상 클래스·다형성·예외를 적용한 텍스트 RPG
 * - 4단계: 던전에서 반복 전투 (Dungeon)
 * - 5단계: 직업 3개와 스킬 (Warrior, Mage, Archer, Skill)
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.print("캐릭터 이름: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = "용사";

        System.out.print("특성 선택 (1.불 2.물 3.풀): ");
        Element element = switch (sc.nextLine().trim()) {
            case "2" -> Element.WATER;
            case "3" -> Element.GRASS;
            default -> Element.FIRE;
        };

        System.out.print("직업 선택 (1.전사 2.마법사 3.궁수): ");
        Hero hero = switch (sc.nextLine().trim()) {   // 다형성
            case "2" -> new Mage(name, element);
            case "3" -> new Archer(name, element);
            default -> new Warrior(name, element);
        };
        hero.getInventory().add(new Item("빨간 포션", ItemType.POTION, 40));
        hero.getInventory().add(new Item("빨간 포션", ItemType.POTION, 40));

        Dungeon dungeon = new Dungeon("어둠의 숲", 5);
        System.out.println("\n" + hero.getJobName() + " "
                + hero.getName() + "의 모험이 시작됩니다!");

        boolean running = true;
        while (running && hero.isAlive()) {
            System.out.println();
            hero.showStatus();
            System.out.println("1.던전  2.인벤토리  3.휴식  0.종료");
            System.out.print("선택> ");
            switch (sc.nextLine().trim()) {
                case "1" -> dungeon.explore(hero, sc, random);
                case "2" -> openInventory(hero, sc);
                case "3" -> {
                    hero.rest();
                    System.out.println("푹 쉬었다. HP·MP 회복!");
                }
                case "0" -> running = false;
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
        if (!hero.isAlive()) {
            System.out.println("\n" + hero.getName()
                    + "이(가) 쓰러졌다... GAME OVER");
        }
        System.out.println("게임을 종료합니다.");
    }

    static void openInventory(Hero hero, Scanner sc) {
        Inventory inv = hero.getInventory();
        inv.print();
        if (inv.isEmpty()) return;
        System.out.print("사용할 번호 (s: 정렬, 0: 닫기)> ");
        String input = sc.nextLine().trim();
        if (input.equals("s")) {
            inv.sort();
            inv.print();
            return;
        }
        try {
            int no = Integer.parseInt(input);
            if (no == 0) return;
            hero.useItem(no - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 번호입니다.");
        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
    }
}
