package com.example.hotelelite.models;

import java.util.Date;

public class Food {

    private String id;
    private String userId;
    private String foodType;
    private long quantity;
    private String deliveryAddress;
    private String contactNum;
    private Date orderTime;
    private Date deliveryTime;
    private long totalAmount;

    public Food() {
    }

    public Food(String id, String userId, String foodType, long quantity, String deliveryAddress, String contactNum, Date orderTime, Date deliveryTime, long totalAmount) {
        this.id = id;
        this.userId = userId;
        this.foodType = foodType;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.contactNum = contactNum;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
