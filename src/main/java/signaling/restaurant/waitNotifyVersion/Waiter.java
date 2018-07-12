package signaling.restaurant.waitNotifyVersion;

class Waiter implements Runnable {
    private final String name;
    private Request currentRequest;

    public Waiter(String name) {
        this.name = name;
    }

    private void waitForCustomerRequest() {
        synchronized (BarRestaurantSimulation.requests) {
            while (true) {
                currentRequest = BarRestaurantSimulation.requests.poll();
                if (currentRequest != null) {
                    break;
                }
                if (BarRestaurantSimulation.numCustomerInBar.get() == 0) {
                    if (BarRestaurantSimulation.closed) {
                        break;
                    }
                }
                try {
                    BarRestaurantSimulation.requests.wait();
                } catch (InterruptedException e) {
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

    private void serverCustomer() {
        System.out.println(name + " is serving food");
    }

    private void getCheck() {
        System.out.println(name + " is getting check");
    }

    @Override
    public void run() {
        System.out.println(name + " has shown up for work");
        while (!BarRestaurantSimulation.closed || BarRestaurantSimulation.numCustomerInBar.get() > 0) {
            waitForCustomerRequest();
            if (currentRequest != null) {
                switch (currentRequest.getRequestType()) {
                    case Request.SEATING_REQUEST:
                        seatCustomer();
                        break;
                    case Request.ORDER_REQUEST:
                        takeOrder();
                        break;
                    case Request.SERVE_REQUEST:
                        seatCustomer();
                        break;
                    case Request.CHECK_REQUEST:
                        getCheck();
                        break;
                }
                synchronized (currentRequest) {
                    currentRequest.setRequestBeingHandled(true);
                    currentRequest.notify();
                }
            }

        }
        System.out.println(name + " is going home");
    }
}
