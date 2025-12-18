package com.ruoyi.food.domain.vo;

public class FoodGroupStatVO {

    /** 食物分类 */
    private String foodGroup;

    /** 分类下食物数量 */
    private Integer count;

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}