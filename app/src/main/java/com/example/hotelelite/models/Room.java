package com.example.hotelelite.models;

import java.util.Date;

public class Room {

    private String id;
    private String userId;
    private String roomType;
    private long numOfRooms;
    private Date checkingIn;
    private Date checkingOut;
    private long perDay;
    private long totalDays;
    private long totalAmount;

    public Room() {
    }

    public Room(String id, String userId, String roomType, long numOfRooms, Date checkingIn, Date checkingOut, long perDay, long totalDays, long totalAmount) {
        this.id = id;
        this.userId = userId;
        this.roomType = roomType;
        this.numOfRooms = numOfRooms;
        this.checkingIn = checkingIn;
        this.checkingOut = checkingOut;
        this.perDay = perDay;
        this.totalDays = totalDays;
        this.totalAmount = totalAmount;
    }

    public long getPerDay() {
        return perDay;
    }

    public void setPerDay(long perDay) {
        this.perDay = perDay;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public long getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(long numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public Date getCheckingIn() {
        return checkingIn;
    }

    public void setCheckingIn(Date checkingIn) {
        this.checkingIn = checkingIn;
    }

    public Date getCheckingOut() {
        return checkingOut;
    }

    public void setCheckingOut(Date checkingOut) {
        this.checkingOut = checkingOut;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
