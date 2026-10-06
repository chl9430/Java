package game;

import battle.Battle;
import battle.ElementDamagePolicy;
import common.Dice;
import common.RandomDice;
import item.Item;
import item.ItemType;
import quest.EventBus;
import quest.HuntQuest;
import save.SaveService;
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

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * [5일차] 상태 패턴(상태이상) · 옵저버 패턴(퀘스트) · 파일 저장 · JUnit 테스트
 * Main = 조립 담당(Assembler)
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ----- 부품 조립 -----
        Dice dice = new RandomDice();
        EventBus bus = new EventBus();
        Battle battle = new Battle(new ElementDamagePolicy(dice), dice, bus);
        Place bag = new InventoryMenu(sc);
        Place dungeon = new Dungeon("어둠의 숲", 5, battle,
                new MonsterFactory(dice), bag, sc);
        Place inn = new Inn();
        SaveService save = new SaveService(Path.of("save.txt"));

        Hero hero = loadOrCreate(sc, save);

        // 옵저버: 퀘스트가 이벤트를 듣는다 (Battle 은 퀘스트를 모른다)
        HuntQuest quest = new HuntQuest("슬라임", 3, () -> {
            hero.gainExp(40);
            System.out.println("보상: 경험치 +40");
        });
        bus.subscribe(quest);

        System.out.println("\n" + hero.getJobName() + " "
                + hero.getName() + "의 모험이 시작됩니다!");

        boolean running = true;
        while (running && hero.isAlive()) {
            System.out.println();
            hero.showStatus();
            System.out.println("  퀘스트: " + quest);
            System.out.println("1.던전  2.인벤토리  3.여관  4.저장  0.종료");
            System.out.print("선택> ");
            switch (sc.nextLine().trim()) {
                case "1" -> dungeon.enter(hero);
                case "2" -> bag.enter(hero);
                case "3" -> inn.enter(hero);
                case "4" -> {
                    try {
                        save.save(hero);
                        System.out.println("저장했습니다.");
                    } catch (IOException e) {
                        System.out.println("저장 실패: " + e.getMessage());
                    }
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

    /** 저장 파일이 있으면 불러오고, 없으면 새로 만든다 */
    private static Hero loadOrCreate(Scanner sc, SaveService save) {
        if (save.exists()) {
            System.out.print("저장된 게임을 불러올까요? (y/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                try {
                    return save.load();
                } catch (IOException | RuntimeException e) {
                    System.out.println("불러오기 실패: " + e.getMessage());
                }
            }
        }
        return createHero(sc);
    }

    private static Hero createHero(Scanner sc) {
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
        return hero;
    }
}
