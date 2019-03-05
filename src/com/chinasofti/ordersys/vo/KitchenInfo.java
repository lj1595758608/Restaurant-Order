package com.chinasofti.ordersys.vo;

public class KitchenInfo {

    private int tableId;
    private int num;
    private String dishesName;
    private int dishesState;
    private int odId;

    public int getOdId() {
        return odId;
    }

    public void setOdId(int odId) {
        this.odId = odId;
    }

    public int getDishesState() {
        return dishesState;
    }

    public void setDishesState(int dishesState) {
        this.dishesState = dishesState;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }


}
