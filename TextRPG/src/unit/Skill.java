package unit;

import battle.DamagePolicy;

/**
 * 스킬 = 이름 + MP 소모량 + 효과(람다)
 */
public class Skill {
    private final String name;
    private final int mpCost;
    private final SkillEffect effect;

    public Skill(String name, int mpCost, SkillEffect effect) {
        this.name = name;
        this.mpCost = mpCost;
        this.effect = effect;
    }

    public String getName() { return name; }
    public int getMpCost() { return mpCost; }

    public int use(Hero user, Monster target, DamagePolicy policy) {
        return effect.apply(user, target, policy);
    }

    @Override
    public String toString() {
        return name + " (MP " + mpCost + ")";
    }
}
