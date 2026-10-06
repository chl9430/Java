package quest;

/** 사냥 퀘스트: 특정 몬스터를 goal 마리 잡으면 완료 */
public class HuntQuest implements GameListener {
    private final String target;
    private final int goal;
    private final Runnable onComplete;   // 완료 시 보상 (밖에서 주입)
    private int count = 0;

    public HuntQuest(String target, int goal, Runnable onComplete) {
        this.target = target;
        this.goal = goal;
        this.onComplete = onComplete;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (isDone() || !event.type().equals("KILL")
                || !event.target().equals(target)) {
            return;
        }
        count++;
        if (isDone()) {
            System.out.println("★ 퀘스트 완료! (" + target + " " + goal + "마리)");
            onComplete.run();
        }
    }

    public boolean isDone() {
        return count >= goal;
    }

    @Override
    public String toString() {
        return target + " " + goal + "마리 사냥 (" + count + "/" + goal + ")"
                + (isDone() ? " 완료" : "");
    }
}
