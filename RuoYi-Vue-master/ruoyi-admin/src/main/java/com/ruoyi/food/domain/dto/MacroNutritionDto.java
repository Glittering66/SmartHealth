package com.ruoyi.food.domain.dto;

public class MacroNutritionDto {

    /** 热量（kcal） */
    private Float calories;

    /** 总脂肪（g） */
    private Float fat;

    /** 饱和脂肪（g） */
    private Float saturatedFat;

    /** 反式脂肪（g） */
    private Float transFat;

    /** 蛋白质（g） */
    private Float protein;

    /** 碳水化合物（g） */
    private Float carbohydrate;

    /** 净碳水（g） */
    private Float netCarbs;

    /** 糖（g） */
    private Float sugars;

    /** 膳食纤维（g） */
    private Float fiber;

    /** 胆固醇（mg） */
    private Float cholesterol;

    /** 水分（g） */
    private Float water;

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Float getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getTransFat() {
        return transFat;
    }

    public void setTransFat(Float transFat) {
        this.transFat = transFat;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getNetCarbs() {
        return netCarbs;
    }

    public void setNetCarbs(Float netCarbs) {
        this.netCarbs = netCarbs;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Float getSugars() {
        return sugars;
    }

    public void setSugars(Float sugars) {
        this.sugars = sugars;
    }

    public Float getFiber() {
        return fiber;
    }

    public void setFiber(Float fiber) {
        this.fiber = fiber;
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public Float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Float cholesterol) {
        this.cholesterol = cholesterol;
    }
}
