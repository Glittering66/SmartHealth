package com.ruoyi.workout.mapper;

import java.util.List;
import com.ruoyi.workout.domain.WorkoutLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运动记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@Mapper
public interface WorkoutLogsMapper 
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
     * 删除运动记录
     * 
     * @param logId 运动记录主键
     * @return 结果
     */
    public int deleteWorkoutLogsByLogId(Long logId);

    /**
     * 批量删除运动记录
     * 
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkoutLogsByLogIds(Long[] logIds);
}
