package signaling.restaurant.firstVersion;

class Request {
    public static final String SEATING_REQUEST = "SEAT";
    public static final String ORDER_REQUEST = "ORDER";
    public static final String SERVE_REQUEST = "SERVE";
    public static final String CHECK_REQUEST = "CHECK";

    private Customer customer;
    private String requestType;

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

}
