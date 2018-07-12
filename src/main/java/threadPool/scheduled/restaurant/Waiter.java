package threadPool.scheduled.restaurant;

class Waiter implements Runnable {
    private final String name;
    private final Kitchen kitchen;
    private Request currentRequest;

    public Waiter(String name, Kitchen kitchen) {
        this.name = name;
        this.kitchen = kitchen;
    }

    private void waitForCustomerRequest() {
        synchronized (BarRestaurantSimulation.requests) {
            System.out.println(name + " is waiting for customer request");
            currentRequest = null;

            while (true) {
                try {
                    currentRequest = BarRestaurantSimulation.requests.take();
                } catch (InterruptedException e) {
                }
                if (currentRequest != null) {
                    break;
                }
                if (BarRestaurantSimulation.numCustomerInBar.get() == 0) {
                    if (BarRestaurantSimulation.closed) {
                        break;
                    }
                }
            }
        }
        if (currentRequest != null) {
            System.out.println(name + " has a request from " + currentRequest.getCustomer().getName() + ":" + currentRequest.getRequestType());
        }
    }

    private void seatCustomer() {
        System.out.println(name + " is seating customer");
    }

    private void takeOrder() {
        System.out.println(name + " is taking order");
    }

    private void serveCustomer() {
        System.out.println(name + " is waiting for kitchen to take the meal");
        Request request = currentRequest;
        kitchen.requestMeal(() -> {
            System.out.println(name + " is bringing the meal over.");
            request.setRequestBeingHandled();
            synchronized (request) {
                request.notify();
            }
        });
    }

    private void getCheck() {
        System.out.println(name + " is getting check");
    }

    @Override
    public void run() {
        System.out.println(name + " has shown up for work");

        while (!BarRestaurantSimulation.closed || BarRestaurantSimulation.numCustomerInBar.get() > 0) {
            waitForCustomerRequest();

            if (currentRequest != null)
            {
                switch (currentRequest.getRequestType())
                {
                    case Request.SEATING_REQUEST:
                        seatCustomer();
                        break;
                    case Request.ORDER_REQUEST:
                        takeOrder();
                        break;
                    case Request.SERVE_REQUEST:
                        serveCustomer();
                        break;
                    case Request.CHECK_REQUEST:
                        getCheck();
                        break;
                }
                if(currentRequest.getRequestType() != Request.SERVE_REQUEST){
                    currentRequest.setRequestBeingHandled();
                    synchronized (currentRequest){
                        currentRequest.notify();
                    }
                }
        }

    }
        System.out.println(name +" is going home");
}
}
