package com.ruoyi.food.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 食物摄入记录对象 food_record
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
public class FoodRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 食物ID(关联foods.id) */
    @Excel(name = "食物ID(关联foods.id)")
    private Long foodId;

    /** 食物名称(冗余快照，避免食物表改名影响历史) */
    @Excel(name = "食物名称(冗余快照，避免食物表改名影响历史)")
    private String foodName;

    /** 食物分类(冗余快照) */
    @Excel(name = "食物分类(冗余快照)")
    private String foodGroup;

    /** 餐次(早餐/午餐/晚餐/加餐) */
    @Excel(name = "餐次(早餐/午餐/晚餐/加餐)")
    private String mealType;

    /** 摄入数量 */
    @Excel(name = "摄入数量")
    private BigDecimal amount;

    /** 单位(g/份/个/毫升等) */
    @Excel(name = "单位(g/份/个/毫升等)")
    private String unit;

    /** 摄入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "摄入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date eatenAt;

    /** 热量(可选，kcal) */
    @Excel(name = "热量(可选，kcal)")
    private BigDecimal calories;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setFoodId(Long foodId) 
    {
        this.foodId = foodId;
    }

    public Long getFoodId() 
    {
        return foodId;
    }

    public void setFoodName(String foodName) 
    {
        this.foodName = foodName;
    }

    public String getFoodName() 
    {
        return foodName;
    }

    public void setFoodGroup(String foodGroup) 
    {
        this.foodGroup = foodGroup;
    }

    public String getFoodGroup() 
    {
        return foodGroup;
    }

    public void setMealType(String mealType) 
    {
        this.mealType = mealType;
    }

    public String getMealType() 
    {
        return mealType;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setEatenAt(Date eatenAt) 
    {
        this.eatenAt = eatenAt;
    }

    public Date getEatenAt() 
    {
        return eatenAt;
    }

    public void setCalories(BigDecimal calories) 
    {
        this.calories = calories;
    }

    public BigDecimal getCalories() 
    {
        return calories;
    }

    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("foodId", getFoodId())
            .append("foodName", getFoodName())
            .append("foodGroup", getFoodGroup())
            .append("mealType", getMealType())
            .append("amount", getAmount())
            .append("unit", getUnit())
            .append("eatenAt", getEatenAt())
            .append("calories", getCalories())
            .append("note", getNote())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
