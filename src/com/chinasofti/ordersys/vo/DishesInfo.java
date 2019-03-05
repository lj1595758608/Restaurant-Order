package com.chinasofti.ordersys.vo;

public class DishesInfo {

    private int dishesId;
    private String dishesName;
    private String dishesDiscript;
    private String dishesImg = "1.jpg";
    private String dishesTxt;
    private int recommend;
    private float dishesPrice;

    public int getDishesId() {
        return dishesId;
    }

    public void setDishesId(int dishesId) {
        this.dishesId = dishesId;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public String getDishesDiscript() {
        return dishesDiscript;
    }

    public void setDishesDiscript(String dishesDiscript) {
        this.dishesDiscript = dishesDiscript;
    }

    public String getDishesImg() {
        return dishesImg;
    }

    public void setDishesImg(String dishesImg) {
        this.dishesImg = dishesImg;
    }

    public String getDishesTxt() {
        return dishesTxt;
    }

    public void setDishesTxt(String dishesTxt) {
        this.dishesTxt = dishesTxt;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public float getDishesPrice() {
        return dishesPrice;
    }

    public void setDishesPrice(float dishesPrice) {
        this.dishesPrice = dishesPrice;
    }
}
