package thread.sleepingBartender;

import java.util.concurrent.TimeUnit;

class Bartender implements Runnable {
    int numInterrupted = 0;

    @Override
    public void run() {
        System.out.println("Bartender: i'm running");
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("got called by a customer");
            }
            if (numInterrupted >= 2) {
                System.out.println("too much work! it's done for today");
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                numInterrupted++;
                Thread.currentThread().interrupt();
            }
        }
    }
}
