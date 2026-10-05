package battle;

import unit.Element;

/**
 * 데미지 계산 규칙 (계약). 규칙을 바꾸려면 구현만 갈아 끼운다.
 */
public interface DamagePolicy {
    int damage(int atk, int def, Element attacker, Element target);
}
