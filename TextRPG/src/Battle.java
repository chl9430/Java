import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * 전투 규칙과 진행
 */
public class Battle {

    /** 데미지 = (공격 - 방어 + 랜덤) × 상성 배율, 최소 1 */
    public static int damage(int atk, int def, Element attacker,
                             Element target, Random random) {
        int base = Math.max(1, atk - def + random.nextInt(5));
        return Math.max(1, (int) (base * attacker.multiplier(target)));
    }

    static String effectText(Element attacker, Element target) {
        if (attacker.beats(target)) return " (효과가 굉장했다!)";
        if (target.beats(attacker)) return " (효과가 별로다...)";
        return "";
    }

    /** 전투를 진행한다. 이기면 true, 지거나 도망치면 false */
    public static boolean fight(Hero hero, Monster m,
                                Scanner sc, Random random) {
        System.out.println("\n" + m.getName() + "("
                + m.getElement().getLabel() + "속성)이(가) 나타났다!");
        while (hero.isAlive() && m.isAlive()) {
            System.out.printf("-- %s HP %d MP %d | %s HP %d --%n",
                    hero.getName(), hero.getHp(), hero.getMp(),
                    m.getName(), m.getHp());
            System.out.print("1.공격  2.스킬  3.도망> ");
            String action = sc.nextLine().trim();

            if (action.equals("3")) {
                if (random.nextInt(100) < 50) {
                    System.out.println("무사히 도망쳤다!");
                    return false;
                }
                System.out.println("도망에 실패했다!");
            } else if (action.equals("2")) {
                Integer dmg = chooseSkill(hero, m, sc, random);
                if (dmg == null) continue;      // 취소 or MP 부족 → 다시 선택
                if (dmg > 0) {
                    m.takeDamage(dmg);
                    System.out.println(m.getName() + "에게 " + dmg + " 피해");
                }
            } else {
                int dmg = damage(hero.getAtk(), m.getDef(),
                        hero.getElement(), m.getElement(), random);
                m.takeDamage(dmg);
                System.out.println(hero.getName() + "의 공격! " + dmg + " 피해"
                        + effectText(hero.getElement(), m.getElement()));
            }
            if (!m.isAlive()) break;

            int mDmg = damage(m.getAtk(), hero.getDef(),
                    m.getElement(), hero.getElement(), random);
            hero.takeDamage(mDmg);
            System.out.println(m.getName() + "의 반격! " + mDmg + " 피해"
                    + effectText(m.getElement(), hero.getElement()));
        }
        return hero.isAlive();
    }

    /** 스킬을 골라 사용. 사용하지 못하면 null */
    private static Integer chooseSkill(Hero hero, Monster m,
                                       Scanner sc, Random random) {
        List<Skill> skills = hero.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + skills.get(i));
        }
        System.out.print("  스킬 번호 (0: 취소)> ");
        try {
            int no = Integer.parseInt(sc.nextLine().trim());
            if (no == 0) return null;
            return hero.useSkill(no - 1, m, random);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 번호입니다.");
        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /** 승리 보상: 경험치와 드롭 */
    public static void reward(Hero hero, Monster m, Random random) {
        System.out.println(m.getName() + " 처치! 경험치 +" + m.getExp());
        hero.gainExp(m.getExp());

        Item drop = m.dropItem(random);
        if (drop == null) return;
        try {
            hero.getInventory().add(drop);
            System.out.println(drop + " 획득!");
        } catch (GameException e) {
            System.out.println(e.getMessage() + " "
                    + drop.getName() + "을(를) 버렸다.");
        }
    }
}
