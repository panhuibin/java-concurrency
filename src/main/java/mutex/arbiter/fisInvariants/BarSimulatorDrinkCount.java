package mutex.arbiter.fisInvariants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static mutex.barSimulation.MainAtomic.numOfThreads;

class BarSimulatorDrinkCount {
    private static final int numberOfThreads = 2;
    private static Arbiter arbiter = new Arbiter();

    public static void main(String[] args) {
        Thread t = null;
        for (int i = 0; i < numberOfThreads; i++) {
            String name = "Thread" + i;
            arbiter.register(name);
            t = new Thread(new BarPatron(name), name);
            t.start();
        }
        while (t.isAlive()) {
            displayStatistics();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final results:");
        displayStatistics();
    }

    private static void displayStatistics() {
        for (int i = 0; i < numberOfThreads; i++) {
            String name = "Thread" + i;
            int[] resultSoFar = arbiter.getNumBoughtAndRoundsSoFar(name);
            int roundsBought = resultSoFar[0];
            int roundsSoFar = resultSoFar[1];
            if (roundsBought > 0) {
                int percentage = roundsBought * 100 / roundsSoFar;
                System.out.println("After " + roundsSoFar + " rounds, " + name
                        + " has bought " + roundsBought + " rounds ("
                        + percentage + "%)");
            }
        }
    }

    public static class BarPatron implements Runnable {
        private static final int numBarVisits = 50;
        private final String name;
        private static final CountDownLatch cl = new CountDownLatch(numOfThreads);
        private static final AtomicBoolean roundBeingBought = new AtomicBoolean();
        private static final AtomicInteger numRoundsBought = new AtomicInteger();
        private static final Phaser phaser = new Phaser(numOfThreads);

        public BarPatron(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int visit = 0; visit < numBarVisits; visit++) {
                enterBarAndOrderDrinks(visit);

                try {
                    if (visit % 10 == 0) {
                        Thread.sleep(10);
                    }
                }catch (InterruptedException e){

                }
            }
        }

        private void enterBarAndOrderDrinks(int visit) {
            roundBeingBought.set(false);
            phaser.arriveAndAwaitAdvance();
            if (roundBeingBought.compareAndSet(false, true)) {
                buyDrinks(visit);
                arbiter.roundBought(name);
            } else {
                waitForDrink(visit);
            }
            phaser.arriveAndAwaitAdvance();
        }

        private void waitForDrink(int visit) {
            //System.out.println("Bar visit " + visit + ":" + name + " is waiting for the drinks.");
        }

        private void buyDrinks(int visit) {
            //System.out.println("Bar visit " + visit + ":" + name + " is buying the drinks.");
        }
    }
}
