/**
 * 궁수: 연속 공격과 방어 무시
 */
public final class Archer extends Hero {

    public Archer(String name, Element element) {
        super(name, element, 105, 40, 14, 3);

        // 연속 사격: 공격력 0.8배로 3번
        addSkill(new Skill("연속 사격", 10, (user, target, random) -> {
            int total = 0;
            for (int i = 0; i < 3; i++) {
                total += Battle.damage(user.getAtk() * 4 / 5,
                        target.getDef(), user.getElement(),
                        target.getElement(), random);
            }
            return total;
        }));

        // 급소 사격: 방어력 무시, 1.8배
        addSkill(new Skill("급소 사격", 14, (user, target, random) ->
                Battle.damage(user.getAtk() * 9 / 5, 0,
                        user.getElement(), target.getElement(), random)));
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
