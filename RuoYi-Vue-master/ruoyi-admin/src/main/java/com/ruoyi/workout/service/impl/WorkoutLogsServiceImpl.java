package com.ruoyi.workout.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.workout.mapper.ExerciseSetsMapper;
import com.ruoyi.workout.mapper.WorkoutExerciseDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.workout.mapper.WorkoutLogsMapper;
import com.ruoyi.workout.domain.WorkoutLogs;
import com.ruoyi.workout.service.IWorkoutLogsService;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private WorkoutExerciseDetailsMapper detailMapper;

    @Autowired
    private ExerciseSetsMapper setMapper;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteWorkoutWithAll(Long logId) {

        // 1️⃣ 先删组记录
        setMapper.deleteByLogId(logId);

        // 2️⃣ 再删动作详情
        detailMapper.deleteByLogId(logId);

        // 3️⃣ 最后删主记录
        return workoutLogsMapper.deleteWorkoutLogsByLogId(logId);
    }

    @Override
    public Map<String, Object> statisticCalorieAndDuration(String startTime, String endTime) {
        Long userId = SecurityUtils.getUserId();
        List<Map<String, Object>> list = workoutLogsMapper.statisticCalorieAndDuration(userId, startTime, endTime);

        // 拆分数据为三个列表
        List<String> dateList = new ArrayList<>();
        List<Double> calorieList = new ArrayList<>();
        List<Double> durationList = new ArrayList<>();

        for (Map<String, Object> item : list) {
            dateList.add(item.get("date").toString());
            // 处理空值（若某天无数据，默认0）
            calorieList.add(item.get("total_calories") != null ? Double.parseDouble(item.get("total_calories").toString()) : 0);
            durationList.add(item.get("total_duration") != null ? Double.parseDouble(item.get("total_duration").toString()) : 0);
        }

        return Map.of(
                "dateList", dateList,
                "calorieList", calorieList,
                "durationList", durationList
        );
    }

}
