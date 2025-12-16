package com.ruoyi.workout.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.workout.mapper.WorkoutLogsMapper;
import com.ruoyi.workout.domain.WorkoutLogs;
import com.ruoyi.workout.service.IWorkoutLogsService;

/**
 * 运动记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@Service
public class WorkoutLogsServiceImpl implements IWorkoutLogsService 
{
    @Autowired
    private WorkoutLogsMapper workoutLogsMapper;

    /**
     * 查询运动记录
     * 
     * @param logId 运动记录主键
     * @return 运动记录
     */
    @Override
    public WorkoutLogs selectWorkoutLogsByLogId(Long logId)
    {
        return workoutLogsMapper.selectWorkoutLogsByLogId(logId);
    }

    /**
     * 查询运动记录列表
     * 
     * @param workoutLogs 运动记录
     * @return 运动记录
     */
    @Override
    public List<WorkoutLogs> selectWorkoutLogsList(WorkoutLogs workoutLogs)
    {
        return workoutLogsMapper.selectWorkoutLogsList(workoutLogs);
    }

    /**
     * 新增运动记录
     * 
     * @param workoutLogs 运动记录
     * @return 结果
     */
    @Override
    public int insertWorkoutLogs(WorkoutLogs workoutLogs)
    {
        return workoutLogsMapper.insertWorkoutLogs(workoutLogs);
    }

    /**
     * 修改运动记录
     * 
     * @param workoutLogs 运动记录
     * @return 结果
     */
    @Override
    public int updateWorkoutLogs(WorkoutLogs workoutLogs)
    {
        return workoutLogsMapper.updateWorkoutLogs(workoutLogs);
    }

    /**
     * 批量删除运动记录
     * 
     * @param logIds 需要删除的运动记录主键
     * @return 结果
     */
    @Override
    public int deleteWorkoutLogsByLogIds(Long[] logIds)
    {
        return workoutLogsMapper.deleteWorkoutLogsByLogIds(logIds);
    }

    /**
     * 删除运动记录信息
     * 
     * @param logId 运动记录主键
     * @return 结果
     */
    @Override
    public int deleteWorkoutLogsByLogId(Long logId)
    {
        return workoutLogsMapper.deleteWorkoutLogsByLogId(logId);
    }
}
