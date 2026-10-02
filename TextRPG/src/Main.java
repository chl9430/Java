import java.util.Random;
import java.util.Scanner;

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

        Hero hero = new Hero(name, element);
        hero.inventory.add(new Item("빨간 포션", ItemType.POTION, 40));
        System.out.println("\n" + hero.name + "의 모험이 시작됩니다!");

        boolean running = true;
        while (running && hero.isAlive()) {
            System.out.println();
            hero.showStatus();
            System.out.println("1.사냥  2.인벤토리  0.종료");
            System.out.print("선택> ");
            switch (sc.nextLine().trim()) {
                case "1" -> hunt(hero, sc, random);
                case "2" -> openInventory(hero, sc);
                case "0" -> running = false;
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
        if (!hero.isAlive()) {
            System.out.println("\n" + hero.name
                    + "이(가) 쓰러졌다... GAME OVER");
        }
        System.out.println("게임을 종료합니다.");
    }

    static void hunt(Hero hero, Scanner sc, Random random) {
        Monster m = Monster.spawn(hero.level, random);
        boolean win = Battle.fight(hero, m, sc, random);
        if (!win) return;

        System.out.println(m.name + " 처치! 경험치 +" + m.exp);
        hero.gainExp(m.exp);

        Item drop = m.dropItem(random);
        if (drop != null) {
            if (hero.inventory.add(drop)) {
                System.out.println(drop + " 획득!");
            } else {
                System.out.println("가방이 가득 차 "
                        + drop.name + "을(를) 버렸다.");
            }
        }
    }

    static void openInventory(Hero hero, Scanner sc) {
        hero.inventory.print();
        if (hero.inventory.isEmpty()) return;
        System.out.print("사용/장착할 번호 (0: 닫기)> ");
        String input = sc.nextLine().trim();
        if (!input.matches("\\d+")) return;  // 숫자만 허용
        int no = Integer.parseInt(input);
        if (no >= 1 && no <= hero.inventory.size()) {
            hero.useItem(no - 1);
        }
    }
}
