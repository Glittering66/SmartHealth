package com.ruoyi.workout.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 训练项目对象 workout_exercise_details
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public class WorkoutExerciseDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long detailId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long logId;

    /** 训练项目名称 */
    @Excel(name = "训练项目名称")
    private String exerciseName;

    /** 目标肌群 */
    @Excel(name = "目标肌群")
    private String muscleGroup;

    /** 使用器械 */
    @Excel(name = "使用器械")
    private String equipmentUsed;

    /** 难度等级 */
    @Excel(name = "难度等级")
    private String difficultyLevel;

    /** 目标组数 */
    @Excel(name = "目标组数")
    private Long targetSets;

    /** 目标次数 */
    @Excel(name = "目标次数")
    private Long targetReps;

    /** 组间休息时间(秒) */
    @Excel(name = "组间休息时间(秒)")
    private Long restSeconds;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }

    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }

    public void setExerciseName(String exerciseName) 
    {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() 
    {
        return exerciseName;
    }

    public void setMuscleGroup(String muscleGroup) 
    {
        this.muscleGroup = muscleGroup;
    }

    public String getMuscleGroup() 
    {
        return muscleGroup;
    }

    public void setEquipmentUsed(String equipmentUsed) 
    {
        this.equipmentUsed = equipmentUsed;
    }

    public String getEquipmentUsed() 
    {
        return equipmentUsed;
    }

    public void setDifficultyLevel(String difficultyLevel) 
    {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDifficultyLevel() 
    {
        return difficultyLevel;
    }

    public void setTargetSets(Long targetSets) 
    {
        this.targetSets = targetSets;
    }

    public Long getTargetSets() 
    {
        return targetSets;
    }

    public void setTargetReps(Long targetReps) 
    {
        this.targetReps = targetReps;
    }

    public Long getTargetReps() 
    {
        return targetReps;
    }

    public void setRestSeconds(Long restSeconds) 
    {
        this.restSeconds = restSeconds;
    }

    public Long getRestSeconds() 
    {
        return restSeconds;
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
            .append("detailId", getDetailId())
            .append("logId", getLogId())
            .append("exerciseName", getExerciseName())
            .append("muscleGroup", getMuscleGroup())
            .append("equipmentUsed", getEquipmentUsed())
            .append("difficultyLevel", getDifficultyLevel())
            .append("targetSets", getTargetSets())
            .append("targetReps", getTargetReps())
            .append("restSeconds", getRestSeconds())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
