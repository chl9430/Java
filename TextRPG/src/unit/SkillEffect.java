package unit;

import battle.DamagePolicy;

/**
 * 스킬 효과 (함수형 인터페이스 → 람다로 구현)
 * 몬스터에게 줄 데미지를 돌려준다. 공격이 아니면 0.
 * 데미지 계산은 DamagePolicy를 받아서 한다 (메서드 주입).
 */
@FunctionalInterface
public interface SkillEffect {
    int apply(Hero user, Monster target, DamagePolicy policy);
}
