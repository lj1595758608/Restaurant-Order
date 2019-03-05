package com.chinasofti.ordersys.vo;


import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class OrderInfo {

    private int orderId;
    private Date orderBeginDate;
    private Date orderEndDate;
    private int waiterId;
    private int orderState;
    private int dishesState;

    public int getDishesState() {
        return dishesState;
    }

    public void setDishesState(int dishesState) {
        this.dishesState = dishesState;
    }

    private int tableId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderBeginDate() {
        return orderBeginDate;
    }

    public void setOrderBeginDate(Date orderBeginDate) {
        this.orderBeginDate = orderBeginDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

}
