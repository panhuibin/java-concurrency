package sharedMemory.unsynced;

public class Consumer implements Runnable {
    Producer producer;

    public Consumer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        System.out.println("Consumer starting");
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            Produce produce = producer.getLastProduce();
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
}
