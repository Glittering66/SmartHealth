package com.ruoyi.food.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 食物营养素对象 nutrients_macro
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
public class NutrientsMacro extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long foodId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long calories;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long fat;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long saturatedFat;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long transFat;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long protein;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long carbohydrate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long netCarbs;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long sugars;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long fiber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long cholesterol;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long water;

    public void setFoodId(Long foodId) 
    {
        this.foodId = foodId;
    }

    public Long getFoodId() 
    {
        return foodId;
    }

    public void setCalories(Long calories) 
    {
        this.calories = calories;
    }

    public Long getCalories() 
    {
        return calories;
    }

    public void setFat(Long fat) 
    {
        this.fat = fat;
    }

    public Long getFat() 
    {
        return fat;
    }

    public void setSaturatedFat(Long saturatedFat) 
    {
        this.saturatedFat = saturatedFat;
    }

    public Long getSaturatedFat() 
    {
        return saturatedFat;
    }

    public void setTransFat(Long transFat) 
    {
        this.transFat = transFat;
    }

    public Long getTransFat() 
    {
        return transFat;
    }

    public void setProtein(Long protein) 
    {
        this.protein = protein;
    }

    public Long getProtein() 
    {
        return protein;
    }

    public void setCarbohydrate(Long carbohydrate) 
    {
        this.carbohydrate = carbohydrate;
    }

    public Long getCarbohydrate() 
    {
        return carbohydrate;
    }

    public void setNetCarbs(Long netCarbs) 
    {
        this.netCarbs = netCarbs;
    }

    public Long getNetCarbs() 
    {
        return netCarbs;
    }

    public void setSugars(Long sugars) 
    {
        this.sugars = sugars;
    }

    public Long getSugars() 
    {
        return sugars;
    }

    public void setFiber(Long fiber) 
    {
        this.fiber = fiber;
    }

    public Long getFiber() 
    {
        return fiber;
    }

    public void setCholesterol(Long cholesterol) 
    {
        this.cholesterol = cholesterol;
    }

    public Long getCholesterol() 
    {
        return cholesterol;
    }

    public void setWater(Long water) 
    {
        this.water = water;
    }

    public Long getWater() 
    {
        return water;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("foodId", getFoodId())
            .append("calories", getCalories())
            .append("fat", getFat())
            .append("saturatedFat", getSaturatedFat())
            .append("transFat", getTransFat())
            .append("protein", getProtein())
            .append("carbohydrate", getCarbohydrate())
            .append("netCarbs", getNetCarbs())
            .append("sugars", getSugars())
            .append("fiber", getFiber())
            .append("cholesterol", getCholesterol())
            .append("water", getWater())
            .toString();
    }
}
