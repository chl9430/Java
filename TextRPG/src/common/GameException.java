package common;

/**
 * 게임 규칙 위반 (MP 부족, 가방 가득 참 등)
 * RuntimeException을 상속 → unchecked 예외
 */
public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }
}
