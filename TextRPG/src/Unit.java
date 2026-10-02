/**
 * Hero와 Monster의 공통 부모 (추상 클래스)
 * - 2일차에 두 클래스에 중복되던 필드와 메서드를 모았다
 * - HP는 private: 반드시 takeDamage / heal 로만 바뀐다
 */
public abstract class Unit {
    private final String name;
    private final Element element;
    protected int maxHp;
    private int hp;
    protected int atk;
    protected int def;

    protected Unit(String name, Element element,
                   int maxHp, int atk, int def) {
        this.name = name;
        this.element = element;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.atk = atk;
        this.def = def;
    }

    public String getName() { return name; }
    public Element getElement() { return element; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }

    /** 자식 클래스가 재정의할 수 있다 (Hero: 무기 공격력 추가) */
    public int getAtk() { return atk; }
    public int getDef() { return def; }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    /** 실제로 회복한 양을 돌려준다 */
    public int heal(int amount) {
        int before = hp;
        hp = Math.min(maxHp, hp + amount);
        return hp - before;
    }
}
