package world;

import unit.Hero;

/**
 * 영웅이 들어가서 무언가를 하는 곳 (던전, 여관, 가방 ...)
 * 사용하는 쪽은 Place만 안다 → 새 장소를 추가해도 호출 코드는 그대로 (OCP)
 */
public interface Place {
    void enter(Hero hero);
}
