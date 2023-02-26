package lk.ijse.scaleShop.to;

import lk.ijse.scaleShop.view.tm.PlaceOrderTM;

import java.util.ArrayList;

public class PlaceOrder {
    private String customerId;
    private String orderId;
    private ArrayList<PlaceOrderTM> orderDetails = new ArrayList<>();


    public PlaceOrder() {
    }

    public PlaceOrder(String customerId, String orderId, ArrayList<PlaceOrderTM> orderDetails) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<PlaceOrderTM> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<PlaceOrderTM> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "customerId='" + customerId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}


