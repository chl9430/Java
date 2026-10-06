package battle;

import common.Dice;
import common.GameException;
import quest.EventBus;
import quest.GameEvent;
import unit.Hero;
import unit.Monster;
import unit.Skill;
import unit.Element;

import java.util.List;
import java.util.Scanner;

/**
 * 전투 진행. 규칙(DamagePolicy)과 랜덤(Dice), 이벤트 게시판(EventBus)은
 * 밖에서 받는다 (DI).
 */
public class Battle {
    private final DamagePolicy policy;
    private final Dice dice;
    private final EventBus bus;

    public Battle(DamagePolicy policy, Dice dice, EventBus bus) {
        this.policy = policy;
        this.dice = dice;
        this.bus = bus;
    }

    /** 같은 패키지 안에서만 쓰는 도우미 (default 접근) */
    static String effectText(Element attacker, Element target) {
        if (attacker.beats(target)) return " (효과가 굉장했다!)";
        if (target.beats(attacker)) return " (효과가 별로다...)";
        return "";
    }

    /** 전투를 진행한다. 이기면 true, 지거나 도망치면 false */
    public boolean fight(Hero hero, Monster m, Scanner sc) {
        System.out.println("\n" + m.getName() + "("
                + m.getElement().getLabel() + "속성)이(가) 나타났다!");
        while (hero.isAlive() && m.isAlive()) {
            System.out.printf("-- %s HP %d MP %d | %s HP %d --%n",
                    hero.getName(), hero.getHp(), hero.getMp(),
                    m.getName(), m.getHp());

            // ----- 영웅 차례 (상태이상 먼저 적용) -----
            boolean heroActs = hero.startTurn();
            if (!hero.isAlive()) break;
            if (!heroActs) {
                System.out.println(hero.getName() + "은(는) 움직일 수 없다!");
            } else {
                System.out.print("1.공격  2.스킬  3.도망> ");
                String action = sc.nextLine().trim();

                if (action.equals("3")) {
                    if (dice.nextInt(100) < 50) {
                        System.out.println("무사히 도망쳤다!");
                        return false;
                    }
                    System.out.println("도망에 실패했다!");
                } else if (action.equals("2")) {
                    Integer dmg = chooseSkill(hero, m, sc);
                    if (dmg == null) continue;  // 취소 or MP 부족 → 다시 선택
                    if (dmg > 0) {
                        m.takeDamage(dmg);
                        System.out.println(m.getName() + "에게 " + dmg + " 피해");
                    }
                } else {
                    int dmg = policy.damage(hero.getAtk(), m.getDef(),
                            hero.getElement(), m.getElement());
                    m.takeDamage(dmg);
                    System.out.println(hero.getName() + "의 공격! " + dmg + " 피해"
                            + effectText(hero.getElement(), m.getElement()));
                }
            }
            if (!m.isAlive()) break;

            // ----- 몬스터 차례 -----
            boolean monsterActs = m.startTurn();
            if (!m.isAlive()) break;
            if (!monsterActs) {
                System.out.println(m.getName() + "은(는) 움직일 수 없다!");
                continue;
            }
            int mDmg = policy.damage(m.getAtk(), hero.getDef(),
                    m.getElement(), hero.getElement());
            hero.takeDamage(mDmg);
            System.out.println(m.getName() + "의 반격! " + mDmg + " 피해"
                    + effectText(m.getElement(), hero.getElement()));
        }
        return hero.isAlive();
    }

    /** 스킬을 골라 사용. 사용하지 못하면 null */
    private Integer chooseSkill(Hero hero, Monster m, Scanner sc) {
        List<Skill> skills = hero.getSkills();
        for (int i = 0; i < skills.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + skills.get(i));
        }
        System.out.print("  스킬 번호 (0: 취소)> ");
        try {
            int no = Integer.parseInt(sc.nextLine().trim());
            if (no == 0) return null;
            return hero.useSkill(no - 1, m, policy);   // 메서드 주입
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 번호입니다.");
        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /** 승리 보상: 경험치와 드롭, 그리고 "처치했다"는 소식 알리기 */
    public void reward(Hero hero, Monster m) {
        System.out.println(m.getName() + " 처치! 경험치 +" + m.getExp());
        hero.gainExp(m.getExp());
        bus.publish(new GameEvent("KILL", m.getName()));   // 누가 듣는지는 모른다

        m.dropItem(dice).ifPresent(drop -> {
            try {
                hero.getInventory().add(drop);
                System.out.println(drop + " 획득!");
            } catch (GameException e) {
                System.out.println(e.getMessage() + " "
                        + drop.getName() + "을(를) 버렸다.");
            }
        });
    }
}
