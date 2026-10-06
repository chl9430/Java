package status;

import unit.Unit;

/**
 * 상태이상 (상태 패턴): 상태마다 "턴이 시작될 때 하는 일"이 다르다.
 * Unit은 어떤 상태인지 몰라도 이 약속만 보고 처리한다.
 */
public interface StatusEffect {
    /** 턴 시작 시 효과 적용. 이번 턴에 행동할 수 있으면 true */
    boolean onTurnStart(Unit target);

    /** 지속 턴이 끝났는가? */
    boolean isExpired();
}
