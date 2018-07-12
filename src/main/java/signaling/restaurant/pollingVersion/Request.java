package signaling.restaurant.pollingVersion;

class Request {
    public static final String SEATING_REQUEST = "SEAT";
    public static final String ORDER_REQUEST = "ORDER";
    public static final String SERVE_REQUEST = "SERVE";
    public static final String CHECK_REQUEST = "CHECK";

    private Customer customer;
    private String requestType;
    private boolean isRequestBeingHandled;

    public Request(Customer customer, String requestType) {
        this.customer = customer;
        this.requestType = requestType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestBeingHandled(){
        isRequestBeingHandled = true;
    }

    public boolean getRequestBeingHandled() {
        return isRequestBeingHandled;
    }
}
