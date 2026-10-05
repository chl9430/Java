package common;

/**
 * 랜덤을 인터페이스로 분리 (DI)
 * 실제 게임은 RandomDice, 테스트는 항상 같은 값을 주는 가짜를 넣는다.
 */
@FunctionalInterface
public interface Dice {
    /** 0 이상 bound 미만의 정수 */
    int nextInt(int bound);
}
