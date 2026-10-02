/**
 * 마법사: MP가 많고 스킬이 강하다
 */
public final class Mage extends Hero {

    public Mage(String name, Element element) {
        super(name, element, 90, 60, 15, 2);

        // 파이어볼: 공격력 2.5배, 항상 불 속성으로 계산
        addSkill(new Skill("파이어볼", 10, (user, target, random) ->
                Battle.damage(user.getAtk() * 5 / 2, target.getDef(),
                        Element.FIRE, target.getElement(), random)));

        // 힐: 최대 HP의 40% 회복 (공격 아님 → 0)
        addSkill(new Skill("힐", 12, (user, target, random) -> {
            int healed = user.heal(user.getMaxHp() * 40 / 100);
            System.out.println("HP " + healed + " 회복!");
            return 0;
        }));
    }

    @Override
    public String getJobName() {
        return "마법사";
    }

    @Override
    protected void growStats() {
        maxHp += 15;
        maxMp += 10;
        atk += 4;
        def += 1;
    }
}
