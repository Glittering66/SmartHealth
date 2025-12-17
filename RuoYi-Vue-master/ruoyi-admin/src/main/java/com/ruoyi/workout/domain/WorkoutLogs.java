package com.ruoyi.workout.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 运动记录对象 workout_logs
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public class WorkoutLogs extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long logId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long userId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date workoutDate;


    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String workoutName;

    /** 总训练时长(分钟) */
    @Excel(name = "总训练时长(分钟)")
    private Long totalDuration;

    /** 预估总消耗卡路里 */
    @Excel(name = "预估总消耗卡路里")
    private BigDecimal totalCalories;

    /** 训练备注 */
    @Excel(name = "训练备注")
    private String notes;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updatedAt;

    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setWorkoutDate(Date workoutDate) 
    {
        this.workoutDate = workoutDate;
    }

    public Date getWorkoutDate() 
    {
        return workoutDate;
    }

    public void setWorkoutName(String workoutName) 
    {
        this.workoutName = workoutName;
    }

    public String getWorkoutName() 
    {
        return workoutName;
    }

    public void setTotalDuration(Long totalDuration) 
    {
        this.totalDuration = totalDuration;
    }

    public Long getTotalDuration() 
    {
        return totalDuration;
    }

    public void setTotalCalories(BigDecimal totalCalories) 
    {
        this.totalCalories = totalCalories;
    }

    public BigDecimal getTotalCalories() 
    {
        return totalCalories;
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

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("userId", getUserId())
            .append("workoutDate", getWorkoutDate())
            .append("workoutName", getWorkoutName())
            .append("totalDuration", getTotalDuration())
            .append("totalCalories", getTotalCalories())
            .append("notes", getNotes())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
