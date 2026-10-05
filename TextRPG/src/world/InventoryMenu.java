package world;

import common.GameException;
import item.Inventory;
import unit.Hero;

import java.util.Scanner;

/** 인벤토리 화면 (3일차에는 Main에 있던 코드 → 책임을 분리) */
public class InventoryMenu implements Place {
    private final Scanner sc;

    public InventoryMenu(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void enter(Hero hero) {
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
