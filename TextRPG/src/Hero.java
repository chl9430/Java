/**
 * 플레이어 캐릭터
 */
public class Hero {
    static final int MAX_LEVEL = 99;

    String name;
    Element element;
    int level = 1;
    int maxHp = 100;
    int hp = 100;
    int baseAtk = 12;
    int baseDef = 3;
    int exp = 0;

    Item weapon;  // 착용 무기 (없으면 null)
    Item armor;   // 착용 방어구 (없으면 null)
    Inventory inventory = new Inventory();

    Hero(String name, Element element) {
        this.name = name;
        this.element = element;
    }

    Hero(String name) {
        this(name, Element.FIRE);  // 특성 기본값: 불
    }

    int getAtk() {
        return baseAtk + (weapon == null ? 0 : weapon.power);
    }

    int getDef() {
        return baseDef + (armor == null ? 0 : armor.power);
    }

    boolean isAlive() {
        return hp > 0;
    }

    void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    int heal(int amount) {
        int before = hp;
        hp = Math.min(maxHp, hp + amount);
        return hp - before;
    }

    void gainExp(int amount) {
        exp += amount;
        while (exp >= level * 30 && level < MAX_LEVEL) {
            exp -= level * 30;
            level++;
            maxHp += 20;
            baseAtk += 3;
            baseDef += 1;
            hp = maxHp;
            System.out.println("*** 레벨 업! Lv." + level + " ***");
        }
    }

    void useItem(int index) {
        Item item = inventory.get(index);
        if (item.type == ItemType.POTION && hp == maxHp) {
            System.out.println("이미 HP가 가득 찼습니다.");
            return;
        }
        inventory.remove(index);
        switch (item.type) {
            case WEAPON -> {
                if (weapon != null) inventory.add(weapon);
                weapon = item;
                System.out.println(item.name + " 장착!");
            }
            case ARMOR -> {
                if (armor != null) inventory.add(armor);
                armor = item;
                System.out.println(item.name + " 장착!");
            }
            case POTION -> {
                int healed = heal(item.power);
                System.out.println("HP " + healed + " 회복!");
            }
        }
    }

    void showStatus() {
        System.out.printf("[%s] %s속성 Lv.%d  HP %d/%d%n",
                name, element.getLabel(), level, hp, maxHp);
        System.out.printf("  공격 %d  방어 %d  EXP %d/%d%n",
                getAtk(), getDef(), exp, level * 30);
        System.out.println("  무기: " + (weapon == null ? "없음" : weapon)
                + "  방어구: " + (armor == null ? "없음" : armor));
    }
}
