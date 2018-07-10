package sharedMemory.synced;

public class Consumer implements Runnable, ProduceObserver{

    Produce produce = null;

    @Override
    public void run() {
        System.out.println("Consumer starting");
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            if (produce != null) {
                int produceInstance = produce.getInstance();
                Produce.Color color = produce.getColor();

                System.out.println("Last produce instance: " + produceInstance);
                System.out.println("Last produce color: " + color);
                if (produceInstance == 10) {
                    break;
                }
            }
        }
    }

    @Override
    public void onProduction(Produce produce) {
        this.produce = produce;
    }
}
