package quest;

/** 이벤트를 듣는 쪽 (옵저버) */
@FunctionalInterface
public interface GameListener {
    void onEvent(GameEvent event);
}
