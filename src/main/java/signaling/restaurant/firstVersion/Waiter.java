package signaling.restaurant.firstVersion;

class Waiter implements Runnable {
    private final String name;
    private Request currentRequest;

    public Waiter(String name) {
        this.name = name;
    }

    private void waitForCustomerRequest() {
        synchronized (BarRestaurantSimulation.requests) {
            currentRequest = BarRestaurantSimulation.requests.poll();
            if (currentRequest != null) {
                System.out.println(name + " has a request from " + currentRequest.getCustomer().getName() + ":" + currentRequest.getRequestType());
            }

        }

    }

    private void seatCustomer() {
        System.out.println(name + " is seating customer");
    }

    private void takeOrder() {
        System.out.println(name + " is taking order");
    }

    private void serveCustomer() {
        System.out.println(name + " is serving food");
    }

    private void getCheck() {
        System.out.println(name + " is getting check");
    }

    @Override
    public void run() {
        System.out.println(name + " has shown up for work.");
        System.out.println(name + " is waiting for customer request.");
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
                        serveCustomer();
                        break;
                    case Request.CHECK_REQUEST:
                        getCheck();
                        break;
                }
                System.out.println(name + " is waiting for a customer request.");
            }

        }
        System.out.println(name + " is going home");
    }
}
