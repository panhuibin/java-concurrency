package mutex.arbiter.fisInvariants;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

class Arbiter {
    private final HashMap<String, Integer> roundsBought = new HashMap<>();
    private final AtomicInteger roundsSoFar = new AtomicInteger();

    public void register(String name) {
        roundsBought.put(name, 0);
    }

    public void roundBought(String name) {
        int numBought = roundsBought.get(name);
        roundsBought.put(name, numBought + 1);
        roundsSoFar.getAndIncrement();
    }

    public int[] getNumBoughtAndRoundsSoFar(String name) {
        return new int[]{roundsBought.get(name), roundsSoFar.get()};
    }
}