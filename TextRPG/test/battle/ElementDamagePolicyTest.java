package battle;

import common.Dice;
import org.junit.jupiter.api.Test;
import unit.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 랜덤을 가짜 Dice로 고정했기 때문에 결과가 항상 같다 (DI의 효과) */
class ElementDamagePolicyTest {
    private final Dice zero = bound -> 0;
    private final DamagePolicy policy = new ElementDamagePolicy(zero);

    @Test
    void 유리한_상성은_1점5배() {
        // (10 - 2) × 1.5 = 12
        assertEquals(12, policy.damage(10, 2, Element.FIRE, Element.GRASS));
    }

    @Test
    void 불리한_상성은_0점7배() {
        // (10 - 2) × 0.7 = 5.6 → 5
        assertEquals(5, policy.damage(10, 2, Element.FIRE, Element.WATER));
    }

    @Test
    void 데미지는_최소_1() {
        assertEquals(1, policy.damage(1, 99, Element.FIRE, Element.FIRE));
    }
}
