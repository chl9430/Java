package unit;

import status.Stun;

/**
 * 전사: 체력과 방어가 높다
 */
public final class Warrior extends Hero {

    public Warrior(String name, Element element) {
        super(name, element, 130, 30, 13, 5);

        // 강타: 공격력 2배
        addSkill(new Skill("강타", 8, (user, target, policy) ->
                policy.damage(user.getAtk() * 2, target.getDef(),
                        user.getElement(), target.getElement())));

        // 흡혈 베기: 1.5배 공격 + 준 피해의 절반 회복
        addSkill(new Skill("흡혈 베기", 12, (user, target, policy) -> {
            int dmg = policy.damage(user.getAtk() * 3 / 2, target.getDef(),
                    user.getElement(), target.getElement());
            System.out.println("HP " + user.heal(dmg / 2) + " 흡수!");
            return dmg;
        }));

        // 방패 강타: 공격 + 1턴 기절
        addSkill(new Skill("방패 강타", 10, (user, target, policy) -> {
            target.addEffect(new Stun(1));
            System.out.println(target.getName() + "이(가) 기절했다!");
            return policy.damage(user.getAtk(), target.getDef(),
                    user.getElement(), target.getElement());
        }));
    }

    @Override
    public String getJobName() {
        return "전사";
    }

    @Override
    protected void growStats() {
        maxHp += 25;
        atk += 3;
        def += 2;
    }
}
