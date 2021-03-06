package threadPool.scheduled.restaurant;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

class BarRestaurantSimulation {
    public static final int TIME_SCALAR = 10;

    private static final int numWaiters = 5;
    private static final int numCustomers = 50;

    static final int barOpenTime = 360;

    static volatile boolean closed = true;

    public static AtomicInteger numCustomerInBar = new AtomicInteger();

    public static BlockingQueue<Request> requests = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen();
        Thread[] waiters = new Thread[numWaiters];
        Thread[] customers = new Thread[numCustomers];

        for (int i = 0; i < numWaiters; i++) {
            String name = "Waiter" + i;
            waiters[i] = new Thread(new Waiter(name, kitchen), name);
        }

        for (int i = 0; i < numCustomers; i++) {
            String name = "Customer" + i;
            customers[i] = new Thread(new Customer(name), name);
        }

        System.out.println("Restaurant is opening!");
        closed = false;
        kitchen.open();

        Arrays.stream(waiters).forEach(waiter -> waiter.start());

        System.out.println("Restaurant is letting in customers!");
        Arrays.stream(customers).forEach(customer -> customer.start());

        try {
            Thread.sleep(barOpenTime * TIME_SCALAR);
        } catch (InterruptedException e) {

        }

        System.out.println("Restaurant is closed to new customers!");
        closed = true;
        Arrays.stream(customers).forEach(customer -> customer.interrupt());

        while (numCustomerInBar.get() > 0) {
            try {
                Thread.sleep(20 * TIME_SCALAR);
            } catch (InterruptedException e) {

            }
        }
        Arrays.stream(waiters).forEach(waiter -> waiter.interrupt());

        kitchen.close();
        System.out.println("Restaurant is closed");
    }

}
