package world;

import unit.Hero;

/** 여관: 쉬면 HP·MP가 가득 찬다 */
public class Inn implements Place {
    @Override
    public void enter(Hero hero) {
        hero.rest();
        System.out.println("푹 쉬었다. HP·MP 회복!");
    }
}
