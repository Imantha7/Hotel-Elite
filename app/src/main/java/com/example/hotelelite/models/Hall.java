package com.example.hotelelite.models;

import java.util.Date;

public class Hall {

    private String id;
    private String userId;
    private String hallType;
    private long numOfTables;
    private Date checkingIn;
    private Date checkingOut;
    private long perDay;
    private long totalDays;
    private long totalAmount;

    public Hall() {
    }

    public Hall(String id, String userId, String hallType, long numOfTables, Date checkingIn, Date checkingOut, long perDay, long totalDays, long totalAmount) {
        this.id = id;
        this.userId = userId;
        this.hallType = hallType;
        this.numOfTables = numOfTables;
        this.checkingIn = checkingIn;
        this.checkingOut = checkingOut;
        this.perDay = perDay;
        this.totalDays = totalDays;
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

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public long getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(long numOfTables) {
        this.numOfTables = numOfTables;
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

    public long getPerDay() {
        return perDay;
    }

    public void setPerDay(long perDay) {
        this.perDay = perDay;
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
