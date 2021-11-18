package com.example.delirush;

public class OrderListData {
    private int orderID;
    private String orderFoodStall;
    private String orderStatus;

    public OrderListData(int orderID, String orderFoodStall, String orderStatus) {
        this.orderID = orderID;
        this.orderFoodStall = orderFoodStall;
        this.orderStatus = orderStatus;
    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderFoodStall() {
        return orderFoodStall;
    }

    public void setOrderFoodStall(String orderFoodStall) {
        this.orderFoodStall = orderFoodStall;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}