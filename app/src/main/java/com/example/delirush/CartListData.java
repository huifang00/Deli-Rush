package com.example.delirush;

public class CartListData {
    private String foodIndex;
    private String food;
    private String quatity;
    private String total;

    public CartListData(String foodIndex, String food, String quatity, String total) {
        this.foodIndex = foodIndex;
        this.food = food;
        this.quatity = quatity;
        this.total = total;
    }

    public String getFoodIndex() {
        return foodIndex;
    }

    public void setFoodIndex(String foodIndex) {
        this.foodIndex = foodIndex;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getQuatity() {
        return quatity;
    }

    public void setQuatity(String quatity) {
        this.quatity = quatity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}