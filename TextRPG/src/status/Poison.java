package status;

import unit.Unit;

/** 중독: 턴마다 피해를 주지만 행동은 할 수 있다 */
public class Poison implements StatusEffect {
    private int turns;
    private final int damage;

    public Poison(int turns, int damage) {
        this.turns = turns;
        this.damage = damage;
    }

    @Override
    public boolean onTurnStart(Unit target) {
        target.takeDamage(damage);
        turns--;
        System.out.println(target.getName() + "은(는) 독 피해 " + damage + "!");
        return true;
    }

    @Override
    public boolean isExpired() {
        return turns <= 0;
    }
}
