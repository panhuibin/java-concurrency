package thread.sleepingBartenderJoin;

import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {
        int numCustomers = 5;
        Bartender bartender = new Bartender();
        Thread bartenderThread = new Thread(bartender, "Bartender");
        bartenderThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {

        }

        Thread[] customerThreads = new Thread[numCustomers];

        for (int i = 0; i < numCustomers; i++) {
            String customerName = "Customer" + i;
            Customer customer = new Customer(bartenderThread, customerName, (int) (Math.random() * 10));
            customerThreads[i] = new Thread(customer, customerName);
            customerThreads[i].start();
        }

        try{
            bartenderThread.join();
        }catch (InterruptedException e){

        }

        System.out.println("where's the bartender?");
        for(Thread customerThread: customerThreads){
            customerThread.interrupt();
        }

    }
}
