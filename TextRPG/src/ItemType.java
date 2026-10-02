public enum ItemType {
    WEAPON("무기"), ARMOR("방어구"), POTION("포션");

    private final String label;

    ItemType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
