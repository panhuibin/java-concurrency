package threadPool.scheduled.restaurant;

import java.util.concurrent.ThreadLocalRandom;

class Customer implements Runnable {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void arrive() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int timeBeforeArrival = Math.abs(tlr.nextInt()) % (BarRestaurantSimulation.barOpenTime + 60);
        try {
            Thread.sleep(timeBeforeArrival * BarRestaurantSimulation.TIME_SCALAR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void waitToBeSeated() {
        ConsumerRequestHandler.act(this, Request.SEATING_REQUEST);
    }

    public void orderMeal() {
        ConsumerRequestHandler.act(this, Request.ORDER_REQUEST);
    }

    public void waitForMeal() {
        ConsumerRequestHandler.act(this, Request.SERVE_REQUEST);
    }

    public void eatMeal() {
        System.out.println(name + " is eating.");
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int eatingTime = tlr.nextInt() % 30 + 30;
        long doneEatingTime = System.currentTimeMillis() + eatingTime * BarRestaurantSimulation.TIME_SCALAR;
        long timeLeft;
        while ((timeLeft = doneEatingTime - System.currentTimeMillis()) > 0) {
            try {
                Thread.sleep(timeLeft);
            } catch (InterruptedException e) {

            }
        }
        System.out.println(name + " has eaten.");
    }

    public void payCheck() {
        ConsumerRequestHandler.act(this, Request.CHECK_REQUEST);
    }

    @Override
    public void run() {
        arrive();
        BarRestaurantSimulation.numCustomerInBar.incrementAndGet();
        if (Thread.interrupted() || BarRestaurantSimulation.closed) {
            System.out.println(name + " has arrived after the closing time, too late!");
            BarRestaurantSimulation.numCustomerInBar.decrementAndGet();
            return;
        }
        System.out.println(name + " has arrived");
        waitToBeSeated();
        orderMeal();
        waitForMeal();
        eatMeal();
        payCheck();
        System.out.println(name + " has left");
        BarRestaurantSimulation.numCustomerInBar.decrementAndGet();
    }
}
