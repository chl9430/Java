import java.util.Random;
import java.util.Scanner;

public class Battle {

    static int damage(int atk, int def, Element attacker,
                      Element target, Random random) {
        int base = Math.max(1, atk - def + random.nextInt(5));
        return Math.max(1, (int) (base * attacker.multiplier(target)));
    }

    static String effectText(Element attacker, Element target) {
        if (attacker.beats(target)) return " (효과가 굉장했다!)";
        if (target.beats(attacker)) return " (효과가 별로다...)";
        return "";
    }

    static boolean fight(Hero hero, Monster m,
                         Scanner sc, Random random) {
        System.out.println("\n야생의 " + m.name + "("
                + m.element.getLabel() + "속성)이(가) 나타났다!");
        while (hero.isAlive() && m.isAlive()) {
            System.out.printf("-- %s HP %d | %s HP %d --%n",
                    hero.name, hero.hp, m.name, m.hp);
            System.out.print("1.공격  2.도망> ");
            String action = sc.nextLine().trim();

            if (action.equals("2")) {
                if (random.nextInt(100) < 50) {
                    System.out.println("무사히 도망쳤다!");
                    return false;
                }
                System.out.println("도망에 실패했다!");
            } else {
                int dmg = damage(hero.getAtk(), m.def,
                        hero.element, m.element, random);
                m.takeDamage(dmg);
                System.out.println(hero.name + "의 공격! " + dmg + " 피해"
                        + effectText(hero.element, m.element));
                if (!m.isAlive()) break;
            }

            int mDmg = damage(m.atk, hero.getDef(),
                    m.element, hero.element, random);
            hero.takeDamage(mDmg);
            System.out.println(m.name + "의 반격! " + mDmg + " 피해"
                    + effectText(m.element, hero.element));
        }
        return hero.isAlive();
    }
}
