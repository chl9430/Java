/**
 * 아이템 종류 (정렬 순서 = 선언 순서)
 */
public enum ItemType {
    WEAPON("무기"), ARMOR("방어구"),
    POTION("체력포션"), MANA_POTION("마나포션");

    private final String label;

    ItemType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
