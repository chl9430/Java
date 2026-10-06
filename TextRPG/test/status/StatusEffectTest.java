package status;

import org.junit.jupiter.api.Test;
import unit.Element;
import unit.Monster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatusEffectTest {
    private Monster dummy() {
        return new Monster("허수아비", Element.FIRE, 50, 1, 1, 0, null, 0);
    }

    @Test
    void 중독은_턴마다_피해를_주고_끝나면_사라진다() {
        Monster m = dummy();
        m.addEffect(new Poison(2, 5));

        assertTrue(m.startTurn());     // 독 피해를 받아도 행동은 가능
        assertTrue(m.startTurn());
        assertEquals(40, m.getHp());

        m.startTurn();                 // 지속 턴이 끝나 더 이상 피해 없음
        assertEquals(40, m.getHp());
    }

    @Test
    void 기절하면_그_턴에_행동할_수_없다() {
        Monster m = dummy();
        m.addEffect(new Stun(1));

        assertFalse(m.startTurn());    // 기절한 턴
        assertTrue(m.startTurn());     // 다음 턴은 정상
    }
}
