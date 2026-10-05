package battle;

import common.Dice;
import unit.Element;

/** 데미지 = (공격 - 방어 + 랜덤) × 상성 배율, 최소 1 */
public class ElementDamagePolicy implements DamagePolicy {
    private final Dice dice;

    public ElementDamagePolicy(Dice dice) {   // 생성자 주입
        this.dice = dice;
    }

    @Override
    public int damage(int atk, int def, Element attacker, Element target) {
        int base = Math.max(1, atk - def + dice.nextInt(5));
        return Math.max(1, (int) (base * attacker.multiplier(target)));
    }
}
