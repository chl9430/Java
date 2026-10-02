/**
 * 전사: 체력과 방어가 높다
 */
public final class Warrior extends Hero {

    public Warrior(String name, Element element) {
        super(name, element, 130, 30, 13, 5);

        // 강타: 공격력 2배
        addSkill(new Skill("강타", 8, (user, target, random) ->
                Battle.damage(user.getAtk() * 2, target.getDef(),
                        user.getElement(), target.getElement(), random)));

        // 흡혈 베기: 1.5배 공격 + 준 피해의 절반 회복
        addSkill(new Skill("흡혈 베기", 12, (user, target, random) -> {
            int dmg = Battle.damage(user.getAtk() * 3 / 2, target.getDef(),
                    user.getElement(), target.getElement(), random);
            System.out.println("HP " + user.heal(dmg / 2) + " 흡수!");
            return dmg;
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
