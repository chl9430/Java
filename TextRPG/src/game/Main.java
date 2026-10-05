package game;

import battle.Battle;
import battle.ElementDamagePolicy;
import common.Dice;
import common.RandomDice;
import item.Item;
import item.ItemType;
import unit.Archer;
import unit.Element;
import unit.Hero;
import unit.Mage;
import unit.Warrior;
import world.Dungeon;
import world.Inn;
import world.InventoryMenu;
import world.MonsterFactory;
import world.Place;

import java.util.Scanner;

/**
 * [4일차] 패키지·DI·Factory·인터페이스로 다시 조립한 텍스트 RPG
 * Main = 조립 담당(Assembler): new 는 여기에 모은다.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ----- 부품 조립 -----
        Dice dice = new RandomDice();
        Battle battle = new Battle(new ElementDamagePolicy(dice), dice);
        Place bag = new InventoryMenu(sc);
        Place dungeon = new Dungeon("어둠의 숲", 5, battle,
                new MonsterFactory(dice), bag, sc);
        Place inn = new Inn();

        // ----- 캐릭터 생성 (3일차와 동일) -----
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
        Hero hero = switch (sc.nextLine().trim()) {
            case "2" -> new Mage(name, element);
            case "3" -> new Archer(name, element);
            default -> new Warrior(name, element);
        };
        hero.getInventory().add(new Item("빨간 포션", ItemType.POTION, 40));
        hero.getInventory().add(new Item("빨간 포션", ItemType.POTION, 40));

        System.out.println("\n" + hero.getJobName() + " "
                + hero.getName() + "의 모험이 시작됩니다!");

        // ----- 메인 루프: 장소는 Place 로만 다룬다 -----
        boolean running = true;
        while (running && hero.isAlive()) {
            System.out.println();
            hero.showStatus();
            System.out.println("1.던전  2.인벤토리  3.여관  0.종료");
            System.out.print("선택> ");
            switch (sc.nextLine().trim()) {
                case "1" -> dungeon.enter(hero);
                case "2" -> bag.enter(hero);
                case "3" -> inn.enter(hero);
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
}
