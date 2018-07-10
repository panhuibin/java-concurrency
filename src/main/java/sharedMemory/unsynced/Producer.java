package sharedMemory.unsynced;

public class Producer implements Runnable {
    private Produce lastProduce = null;

    public Produce getLastProduce() {
        return lastProduce;
    }

    @Override
    public void run() {
        System.out.println("Producer Starting");
        Produce produce = new Produce();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            produce.setInstance(i);
            produce.setColor(Produce.Color.values()[i % Produce.Color.values().length]);
            lastProduce = produce;
        }
        System.out.println("Producer terminating");
    }
}
