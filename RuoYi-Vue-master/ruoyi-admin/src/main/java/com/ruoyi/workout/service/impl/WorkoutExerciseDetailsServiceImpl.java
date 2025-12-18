package com.ruoyi.workout.service.impl;

import java.util.List;

import com.ruoyi.workout.mapper.ExerciseSetsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.workout.mapper.WorkoutExerciseDetailsMapper;
import com.ruoyi.workout.domain.WorkoutExerciseDetails;
import com.ruoyi.workout.service.IWorkoutExerciseDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 训练项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@Service
public class WorkoutExerciseDetailsServiceImpl implements IWorkoutExerciseDetailsService
{
    @Autowired
    private WorkoutExerciseDetailsMapper detailsMapper;

    @Autowired
    private ExerciseSetsMapper workoutSetsMapper;

    @Autowired
    private WorkoutExerciseDetailsMapper workoutExerciseDetailsMapper;

    /**
     * 查询训练项目
     * 
     * @param detailId 训练项目主键
     * @return 训练项目
     */
    @Override
    public WorkoutExerciseDetails selectWorkoutExerciseDetailsByDetailId(Long detailId)
    {
        return workoutExerciseDetailsMapper.selectWorkoutExerciseDetailsByDetailId(detailId);
    }

    /**
     * 查询训练项目列表
     * 
     * @param workoutExerciseDetails 训练项目
     * @return 训练项目
     */
    @Override
    public List<WorkoutExerciseDetails> selectWorkoutExerciseDetailsList(WorkoutExerciseDetails workoutExerciseDetails)
    {
        return workoutExerciseDetailsMapper.selectWorkoutExerciseDetailsList(workoutExerciseDetails);
    }

    /**
     * 新增训练项目
     * 
     * @param workoutExerciseDetails 训练项目
     * @return 结果
     */
    @Override
    public int insertWorkoutExerciseDetails(WorkoutExerciseDetails workoutExerciseDetails)
    {
        return workoutExerciseDetailsMapper.insertWorkoutExerciseDetails(workoutExerciseDetails);
    }

    /**
     * 修改训练项目
     * 
     * @param workoutExerciseDetails 训练项目
     * @return 结果
     */
    @Override
    public int updateWorkoutExerciseDetails(WorkoutExerciseDetails workoutExerciseDetails)
    {
        return workoutExerciseDetailsMapper.updateWorkoutExerciseDetails(workoutExerciseDetails);
    }

    /**
     * 批量删除训练项目
     * 
     * @param detailIds 需要删除的训练项目主键
     * @return 结果
     */
    @Override
    public int deleteWorkoutExerciseDetailsByDetailIds(Long[] detailIds)
    {
        return workoutExerciseDetailsMapper.deleteWorkoutExerciseDetailsByDetailIds(detailIds);
    }

    /**
     * 删除训练项目信息
     * 
     * @param detailId 训练项目主键
     * @return 结果
     */
    @Override
    public int deleteWorkoutExerciseDetailsByDetailId(Long detailId)
    {
        return workoutExerciseDetailsMapper.deleteWorkoutExerciseDetailsByDetailId(detailId);
    }

    @Override
    public List<WorkoutExerciseDetails> selectByLogId(Long logId)
    {
        return workoutExerciseDetailsMapper.selectByLogId(logId);
    }

    /**
     * 级联删除 detail + sets
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDetailWithSets(Long detailId) {

        // 1️⃣ 先删 sets
        workoutSetsMapper.deleteByLogId(detailId);

        // 2️⃣ 再删 detail
        return detailsMapper.deleteWorkoutExerciseDetailsByDetailId(detailId);
    }

}
