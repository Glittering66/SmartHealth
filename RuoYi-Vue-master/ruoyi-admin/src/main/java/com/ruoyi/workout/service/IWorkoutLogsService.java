package com.ruoyi.workout.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.workout.domain.WorkoutLogs;

/**
 * 运动记录Service接口
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public interface IWorkoutLogsService 
{
    /**
     * 查询运动记录
     * 
     * @param logId 运动记录主键
     * @return 运动记录
     */
    public WorkoutLogs selectWorkoutLogsByLogId(Long logId);

    /**
     * 查询运动记录列表
     * 
     * @param workoutLogs 运动记录
     * @return 运动记录集合
     */
    public List<WorkoutLogs> selectWorkoutLogsList(WorkoutLogs workoutLogs);

    /**
     * 新增运动记录
     * 
     * @param workoutLogs 运动记录
     * @return 结果
     */
    public int insertWorkoutLogs(WorkoutLogs workoutLogs);

    /**
     * 修改运动记录
     * 
     * @param workoutLogs 运动记录
     * @return 结果
     */
    public int updateWorkoutLogs(WorkoutLogs workoutLogs);

    /**
     * 批量删除运动记录
     * 
     * @param logIds 需要删除的运动记录主键集合
     * @return 结果
     */
    public int deleteWorkoutLogsByLogIds(Long[] logIds);

    /**
     * 删除运动记录信息
     * 
     * @param logId 运动记录主键
     * @return 结果
     */
    public int deleteWorkoutLogsByLogId(Long logId);

    int deleteWorkoutWithAll(Long logId);

    /**
     * 按日期统计运动
     */
    Map<String, Object> statisticCalorieAndDuration(String startTime, String endTime);

}
