package unit;

import status.Poison;

/**
 * 궁수: 연속 공격과 방어 무시
 */
public final class Archer extends Hero {

    public Archer(String name, Element element) {
        super(name, element, 105, 40, 14, 3);

        // 연속 사격: 공격력 0.8배로 3번
        addSkill(new Skill("연속 사격", 10, (user, target, policy) -> {
            int total = 0;
            for (int i = 0; i < 3; i++) {
                total += policy.damage(user.getAtk() * 4 / 5,
                        target.getDef(), user.getElement(),
                        target.getElement());
            }
            return total;
        }));

        // 급소 사격: 방어력 무시, 1.8배
        addSkill(new Skill("급소 사격", 14, (user, target, policy) ->
                policy.damage(user.getAtk() * 9 / 5, 0,
                        user.getElement(), target.getElement())));

        // 독화살: 약한 공격 + 3턴 동안 중독
        addSkill(new Skill("독화살", 8, (user, target, policy) -> {
            target.addEffect(new Poison(3, 5));
            System.out.println(target.getName() + "이(가) 중독됐다!");
            return policy.damage(user.getAtk() / 2, target.getDef(),
                    user.getElement(), target.getElement());
        }));
    }

    @Override
    public String getJobName() {
        return "궁수";
    }

    @Override
    protected void growStats() {
        maxHp += 20;
        maxMp += 5;
        atk += 4;
        def += 1;
    }
}
