package sharedMemory.synced;

import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable {
    private volatile List<ProduceObserver> observers = new ArrayList<>();

    public void registerObserver(ProduceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void run() {
        System.out.println("Producer Starting");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            Produce.ProduceBuilder builder = new Produce.ProduceBuilder();
            builder.withInstance(i);
            builder.withColor(Produce.Color.values()[i % Produce.Color.values().length]);
            Produce lastProduce = builder.build();
            observers.forEach(observer -> observer.onProduction(lastProduce));
        }
        System.out.println("Producer terminating");
    }
}
