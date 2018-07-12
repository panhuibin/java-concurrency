package threadPool.monteCarlo.threadPool;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class PiTask implements Runnable {
    private int numPoints;
    private PiResult result;

    public PiTask(int numPoints, PiResult result) {
        this.numPoints = numPoints;
        this.result = result;
    }

    public boolean isInCircle(double x, double y) {
        return x * x + y * y <= 1;
    }

    @Override
    public void run() {
        Random rand = ThreadLocalRandom.current();
        int numInCircle = 0;
        for (int i = 0; i < numPoints; i++) {
            if (isInCircle(rand.nextDouble(), rand.nextDouble())) {
                numInCircle++;
            }
        }
        result.setResult(numInCircle);
    }
}
