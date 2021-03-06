package mutex.barSimulation;

import java.util.concurrent.CountDownLatch;

public class MainRaceCondition {
    public static int numOfThreads = 2;


    public static void main(String[] args) {
        for (int i = 0; i < numOfThreads; i++) {
            String name = "Thread" + i;
            (new Thread(new BarPatron(name), name)).start();
        }
    }

    public static class BarPatron implements Runnable{
        private String name;
        private static final CountDownLatch cl = new CountDownLatch(numOfThreads);
        private static volatile boolean roundBeingBought = false;

        public BarPatron(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            enterBarAndOrderDrinks();
        }

        private void enterBarAndOrderDrinks(){
            if(!roundBeingBought){
                try{
                    cl.countDown();
                    cl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                roundBeingBought = true;
                buyDrinks();
            }else{
                waitForDrink();
            }
        }

        private void waitForDrink() {
            System.out.println(name + " is waiting for the drinks.");
        }

        private void buyDrinks() {
            System.out.println(name + " is buying the drinks.");
        }
    }
}
