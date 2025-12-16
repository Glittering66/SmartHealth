package com.ruoyi.workout.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目组数对象 exercise_sets
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public class ExerciseSets extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long setId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long detailId;

    /** 第几组 */
    @Excel(name = "第几组")
    private Long setNumber;

    /** 使用重量(kg) */
    @Excel(name = "使用重量(kg)")
    private BigDecimal weightKg;

    /** 实际完成次数 */
    @Excel(name = "实际完成次数")
    private Long completedReps;

    /** 自觉强度系数(1-10) */
    @Excel(name = "自觉强度系数(1-10)")
    private Long rpeLevel;

    /** 本组耗时(秒) */
    @Excel(name = "本组耗时(秒)")
    private Long setDurationSeconds;

    /** 本组备注 */
    @Excel(name = "本组备注")
    private String notes;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    /** 本组训练预估消耗的卡路里 */
    @Excel(name = "本组训练预估消耗的卡路里")
    private BigDecimal caloriesBurned;

    public void setSetId(Long setId) 
    {
        this.setId = setId;
    }

    public Long getSetId() 
    {
        return setId;
    }

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }

    public void setSetNumber(Long setNumber) 
    {
        this.setNumber = setNumber;
    }

    public Long getSetNumber() 
    {
        return setNumber;
    }

    public void setWeightKg(BigDecimal weightKg) 
    {
        this.weightKg = weightKg;
    }

    public BigDecimal getWeightKg() 
    {
        return weightKg;
    }

    public void setCompletedReps(Long completedReps) 
    {
        this.completedReps = completedReps;
    }

    public Long getCompletedReps() 
    {
        return completedReps;
    }

    public void setRpeLevel(Long rpeLevel) 
    {
        this.rpeLevel = rpeLevel;
    }

    public Long getRpeLevel() 
    {
        return rpeLevel;
    }

    public void setSetDurationSeconds(Long setDurationSeconds) 
    {
        this.setDurationSeconds = setDurationSeconds;
    }

    public Long getSetDurationSeconds() 
    {
        return setDurationSeconds;
    }

    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setCaloriesBurned(BigDecimal caloriesBurned) 
    {
        this.caloriesBurned = caloriesBurned;
    }

    public BigDecimal getCaloriesBurned() 
    {
        return caloriesBurned;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("setId", getSetId())
            .append("detailId", getDetailId())
            .append("setNumber", getSetNumber())
            .append("weightKg", getWeightKg())
            .append("completedReps", getCompletedReps())
            .append("rpeLevel", getRpeLevel())
            .append("setDurationSeconds", getSetDurationSeconds())
            .append("notes", getNotes())
            .append("createdAt", getCreatedAt())
            .append("caloriesBurned", getCaloriesBurned())
            .toString();
    }
}
