package signaling.restaurant.pollingVersion;

class ConsumerRequestHandler {
    private static void placeRequest(Request request) {
        synchronized (BarRestaurantSimulation.requests) {
            BarRestaurantSimulation.requests.offer(request);
        }
    }

    private static void waitForRequestToBeHandled(Request request) {
        while (!request.getRequestBeingHandled()) {
            try {
                Thread.sleep(1 * BarRestaurantSimulation.TIME_SCALAR);
            } catch (InterruptedException e) {

            }
        }
    }

    private static String getPreRequestMessage(String requestType) {
        switch (requestType) {
            case Request.SEATING_REQUEST:
                return " is requesting to be seated";
            case Request.ORDER_REQUEST:
                return " is request to order";
            case Request.SERVE_REQUEST:
                return " is waiting to be served the meal";
            case Request.CHECK_REQUEST:
                return " is requesting the check.";
            default:
                return " is...I'm not sure what they're requesting!";
        }
    }

    private static String getPostActionMessage(String requestType) {
        switch (requestType) {
            case Request.SEATING_REQUEST:
                return " has been seated";
            case Request.ORDER_REQUEST:
                return " has ordered";
            case Request.SERVE_REQUEST:
                return "'s food has arrived";
            case Request.CHECK_REQUEST:
                return " has paid.";
            default:
                return " is...I'm not sure what they've done!";
        }
    }

    public static void act(Customer customer, String requestType) {
        Request request = new Request(customer, requestType);
        System.out.println(customer.getName() + getPreRequestMessage(requestType));
        placeRequest(request);
        System.out.println(customer.getName() + getPostActionMessage(requestType));
    }
}
