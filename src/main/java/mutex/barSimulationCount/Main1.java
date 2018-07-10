package mutex.barSimulationCount;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


class Main1 {
    public static int numOfThreads = 2;


    public static void main(String[] args) {
        for (int i = 0; i < numOfThreads; i++) {
            String name = "Thread" + i;
            (new Thread(new BarPatron(name), name)).start();
        }
    }

    public static class BarPatron implements Runnable {
        private static final int numBarVisits = 50;
        private String name;
        private static final CountDownLatch cl = new CountDownLatch(numOfThreads);
        private static final AtomicBoolean roundBeingBought = new AtomicBoolean();
        private static final AtomicInteger numRoundsBought = new AtomicInteger();

        public BarPatron(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int visit = 0; visit < numBarVisits; visit++) {
                enterBarAndOrderDrinks(visit);
            }
            int percentage = numRoundsBought.get() * 100 / numBarVisits;
            System.out.println(name + " bought " + numRoundsBought
                    + " rounds (" + percentage + "%)");
        }

        private void enterBarAndOrderDrinks(int visit) {
            roundBeingBought.set(false);
            if (roundBeingBought.compareAndSet(false, true)) {
                buyDrinks(visit);
                numRoundsBought.getAndIncrement();
            } else {
                waitForDrink(visit);
            }
        }

        private void waitForDrink(int visit) {
            System.out.println("Bar visit " + visit + ":" +name + " is waiting for the drinks.");
        }

        private void buyDrinks(int visit) {
            System.out.println("Bar visit " + visit + ":" +name + " is buying the drinks.");
        }
    }
}
