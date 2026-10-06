package quest;

/** 게임에서 일어난 일 (record). 예: type "KILL", target "슬라임" */
public record GameEvent(String type, String target) {
}
