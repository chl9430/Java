package common;

import java.util.Random;

public class RandomDice implements Dice {
    private final Random random = new Random();

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
