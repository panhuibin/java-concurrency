package thread.sleepingBartenderJoin;

import java.util.concurrent.TimeUnit;

class Customer implements Runnable {
    private Thread bartenderThread;
    private String name;
    private int waitTime;

    public Customer(Thread bartenderThread, String name, int waitTime) {
        this.bartenderThread = bartenderThread;
        this.name = name;
        this.waitTime = waitTime;
    }

    @Override
    public void run() {
        System.out.println(name + " looks around in the bar for " + waitTime + " s");
        try {
            TimeUnit.SECONDS.sleep(waitTime);

        } catch (InterruptedException e) {
            System.out.println(name + " gets interrupted");
            return;
        }
        System.out.println(name + " calls bartender");
        bartenderThread.interrupt();

    }
}
