/**
 * 특성(속성). 불 → 풀 → 물 → 불 순서로 상성이 있다.
 */
public enum Element {
    FIRE("불"), WATER("물"), GRASS("풀");

    private final String label;

    Element(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /** 내가 target을 이기는 상성인가? */
    public boolean beats(Element target) {
        return switch (this) {
            case FIRE -> target == GRASS;
            case GRASS -> target == WATER;
            case WATER -> target == FIRE;
        };
    }

    /** 공격 배율: 유리 1.5배, 불리 0.7배, 그 외 1배 */
    public double multiplier(Element target) {
        if (beats(target)) return 1.5;
        if (target.beats(this)) return 0.7;
        return 1.0;
    }
}
