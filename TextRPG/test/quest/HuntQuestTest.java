package quest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HuntQuestTest {
    @Test
    void 목표_마릿수를_채우면_완료되고_보상을_준다() {
        AtomicInteger reward = new AtomicInteger();
        HuntQuest quest = new HuntQuest("슬라임", 2, reward::incrementAndGet);
        EventBus bus = new EventBus();
        bus.subscribe(quest);

        bus.publish(new GameEvent("KILL", "슬라임"));
        assertFalse(quest.isDone());
        bus.publish(new GameEvent("KILL", "슬라임"));

        assertTrue(quest.isDone());
        assertEquals(1, reward.get());
    }

    @Test
    void 다른_몬스터는_세지_않는다() {
        HuntQuest quest = new HuntQuest("슬라임", 1, () -> { });
        quest.onEvent(new GameEvent("KILL", "고블린"));
        assertFalse(quest.isDone());
    }
}
