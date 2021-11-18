package com.example.delirush;

public class CartListData {
    private String food;
    private int quantity;
    private String total;

    public CartListData(String food, int quantity, String total) {
        this.food = food;
        this.quantity = quantity;
        this.total = total;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}