package com.example.delirush;

public class OrderListData {
    private String orderID;
    private String orderFoodStall;
    private String orderStatus;

    public OrderListData(String orderID, String orderFoodStall, String orderStatus) {
        this.orderID = orderID;
        this.orderFoodStall = orderFoodStall;
        this.orderStatus = orderStatus;
    }

    public String getOrderFoodStall() {
        return orderFoodStall;
    }

    public void setOrderFoodStall(String orderFoodStall) {
        this.orderFoodStall = orderFoodStall;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}