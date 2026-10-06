package quest;

import java.util.ArrayList;
import java.util.List;

/**
 * 이벤트 게시판 (옵저버 패턴의 Subject)
 * 알리는 쪽(Battle)은 누가 듣는지 모른다. 듣는 쪽은 subscribe 만 하면 된다.
 */
public class EventBus {
    private final List<GameListener> listeners = new ArrayList<>();

    public void subscribe(GameListener listener) {
        listeners.add(listener);
    }

    public void publish(GameEvent event) {
        listeners.forEach(l -> l.onEvent(event));
    }
}
