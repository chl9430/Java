import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 플레이어 캐릭터의 공통 부모 (추상 클래스)
 * 직업(Warrior, Mage, Archer)이 상속해서 완성한다.
 */
public abstract class Hero extends Unit {
    public static final int MAX_LEVEL = 99;

    private int level = 1;
    private int exp = 0;
    protected int maxMp;
    private int mp;

    private Item weapon;
    private Item armor;
    private final Inventory inventory = new Inventory();
    private final List<Skill> skills = new ArrayList<>();

    protected Hero(String name, Element element, int hp,
                   int mp, int atk, int def) {
        super(name, element, hp, atk, def);
        this.maxMp = mp;
        this.mp = mp;
    }

    // ----- 자식이 반드시 구현 -----
    public abstract String getJobName();

    /** 레벨업할 때 직업마다 다르게 성장 */
    protected abstract void growStats();

    // ----- 자식이 생성자에서 스킬 등록 -----
    protected void addSkill(Skill skill) {
        skills.add(skill);
    }

    public List<Skill> getSkills() { return skills; }
    public Inventory getInventory() { return inventory; }
    public int getLevel() { return level; }
    public int getMp() { return mp; }

    /** 부모의 공격력 + 무기 공격력 */
    @Override
    public int getAtk() {
        return super.getAtk()
                + (weapon == null ? 0 : weapon.getPower());
    }

    @Override
    public int getDef() {
        return super.getDef()
                + (armor == null ? 0 : armor.getPower());
    }

    /** 스킬 사용. MP가 부족하면 예외 */
    public int useSkill(int index, Monster target, Random random) {
        Skill skill = skills.get(index);
        if (mp < skill.getMpCost()) {
            throw new GameException("MP가 부족합니다.");
        }
        mp -= skill.getMpCost();
        System.out.println(getName() + "의 " + skill.getName() + "!");
        return skill.use(this, target, random);
    }

    public int restoreMp(int amount) {
        int before = mp;
        mp = Math.min(maxMp, mp + amount);
        return mp - before;
    }

    public void rest() {
        heal(getMaxHp());
        restoreMp(maxMp);
    }

    public void gainExp(int amount) {
        exp += amount;
        while (exp >= level * 30 && level < MAX_LEVEL) {
            exp -= level * 30;
            level++;
            growStats();          // 템플릿 메서드: 성장은 자식에게
            rest();
            System.out.println("*** 레벨 업! Lv." + level + " ***");
        }
    }

    public void useItem(int index) {
        Item item = inventory.get(index);
        switch (item.getType()) {
            case WEAPON -> {
                inventory.remove(index);
                if (weapon != null) inventory.add(weapon);
                weapon = item;
                System.out.println(item.getName() + " 장착!");
            }
            case ARMOR -> {
                inventory.remove(index);
                if (armor != null) inventory.add(armor);
                armor = item;
                System.out.println(item.getName() + " 장착!");
            }
            case POTION -> {
                if (getHp() == getMaxHp()) {
                    throw new GameException("이미 HP가 가득 찼습니다.");
                }
                inventory.remove(index);
                System.out.println("HP " + heal(item.getPower()) + " 회복!");
            }
            case MANA_POTION -> {
                if (mp == maxMp) {
                    throw new GameException("이미 MP가 가득 찼습니다.");
                }
                inventory.remove(index);
                System.out.println("MP " + restoreMp(item.getPower()) + " 회복!");
            }
        }
    }

    public void showStatus() {
        System.out.printf("[%s] %s · %s속성 Lv.%d%n", getName(),
                getJobName(), getElement().getLabel(), level);
        System.out.printf("  HP %d/%d  MP %d/%d  EXP %d/%d%n",
                getHp(), getMaxHp(), mp, maxMp, exp, level * 30);
        System.out.printf("  공격 %d  방어 %d%n", getAtk(), getDef());
        System.out.println("  무기: " + (weapon == null ? "없음" : weapon)
                + "  방어구: " + (armor == null ? "없음" : armor));
    }
}
