package com.ruoyi.food.domain.dto;


public class FoodServingDto {

    /** 单份重量（g） */
    private Float servingWeight;

    /** 份量描述（如：1 碗 / 1 个 / 100g） */
    private String servingDesc;

    public String getServingDesc() {
        return servingDesc;
    }

    public void setServingDesc(String servingDesc) {
        this.servingDesc = servingDesc;
    }

    public Float getServingWeight() {
        return servingWeight;
    }

    public void setServingWeight(Float servingWeight) {
        this.servingWeight = servingWeight;
    }
}