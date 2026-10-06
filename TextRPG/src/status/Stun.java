package status;

import unit.Unit;

/** 기절: 피해는 없지만 행동할 수 없다 */
public class Stun implements StatusEffect {
    private int turns;

    public Stun(int turns) {
        this.turns = turns;
    }

    @Override
    public boolean onTurnStart(Unit target) {
        turns--;
        return false;
    }

    @Override
    public boolean isExpired() {
        return turns <= 0;
    }
}
