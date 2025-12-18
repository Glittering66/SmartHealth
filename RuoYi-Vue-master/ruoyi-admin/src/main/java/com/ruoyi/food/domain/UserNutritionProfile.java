package com.ruoyi.food.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户营养计算基础信息对象 user_nutrition_profile
 * 
 * @author ruoyi
 * @date 2025-12-18
 */
public class UserNutritionProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID（来自 getCurrentUserId） */
    @Excel(name = "用户ID", readConverterExp = "来=自,g=etCurrentUserId")
    private Long userId;

    /** 性别：male / female */
    @Excel(name = "性别：male / female")
    private String gender;

    /** 年龄（岁） */
    @Excel(name = "年龄", readConverterExp = "岁=")
    private Long age;

    /** 身高（cm） */
    @Excel(name = "身高", readConverterExp = "c=m")
    private BigDecimal height;

    /** 体重（kg） */
    @Excel(name = "体重", readConverterExp = "k=g")
    private BigDecimal weight;

    /** 活动系数：1.2 / 1.375 / 1.55 / 1.725 / 1.9 */
    @Excel(name = "活动系数：1.2 / 1.375 / 1.55 / 1.725 / 1.9")
    private BigDecimal activityLevel;

    /** 日照水平：low / mid / high */
    @Excel(name = "日照水平：low / mid / high")
    private String sunLevel;

    /** 是否吸烟：0-否 1-是 */
    @Excel(name = "是否吸烟：0-否 1-是")
    private Integer smoke;

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

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }

    public void setHeight(BigDecimal height) 
    {
        this.height = height;
    }

    public BigDecimal getHeight() 
    {
        return height;
    }

    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }

    public void setActivityLevel(BigDecimal activityLevel) 
    {
        this.activityLevel = activityLevel;
    }

    public BigDecimal getActivityLevel() 
    {
        return activityLevel;
    }

    public void setSunLevel(String sunLevel) 
    {
        this.sunLevel = sunLevel;
    }

    public String getSunLevel() 
    {
        return sunLevel;
    }

    public void setSmoke(Integer smoke) 
    {
        this.smoke = smoke;
    }

    public Integer getSmoke() 
    {
        return smoke;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("gender", getGender())
            .append("age", getAge())
            .append("height", getHeight())
            .append("weight", getWeight())
            .append("activityLevel", getActivityLevel())
            .append("sunLevel", getSunLevel())
            .append("smoke", getSmoke())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
