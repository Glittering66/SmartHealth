package com.ruoyi.workout.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 运动类型对象 exercise_met_reference
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public class ExerciseMetReference extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long exerciseId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String exerciseName;

    /** 代谢当量值 */
    @Excel(name = "代谢当量值")
    private BigDecimal metValue;

    /** 强度分级 */
    @Excel(name = "强度分级")
    private String intensityLevel;

    /** 主要目标肌群 */
    @Excel(name = "主要目标肌群")
    private String muscleGroup;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String description;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    public void setExerciseId(Long exerciseId) 
    {
        this.exerciseId = exerciseId;
    }

    public Long getExerciseId() 
    {
        return exerciseId;
    }

    public void setExerciseName(String exerciseName) 
    {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() 
    {
        return exerciseName;
    }

    public void setMetValue(BigDecimal metValue) 
    {
        this.metValue = metValue;
    }

    public BigDecimal getMetValue() 
    {
        return metValue;
    }

    public void setIntensityLevel(String intensityLevel) 
    {
        this.intensityLevel = intensityLevel;
    }

    public String getIntensityLevel() 
    {
        return intensityLevel;
    }

    public void setMuscleGroup(String muscleGroup) 
    {
        this.muscleGroup = muscleGroup;
    }

    public String getMuscleGroup() 
    {
        return muscleGroup;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("exerciseId", getExerciseId())
            .append("exerciseName", getExerciseName())
            .append("metValue", getMetValue())
            .append("intensityLevel", getIntensityLevel())
            .append("muscleGroup", getMuscleGroup())
            .append("description", getDescription())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
