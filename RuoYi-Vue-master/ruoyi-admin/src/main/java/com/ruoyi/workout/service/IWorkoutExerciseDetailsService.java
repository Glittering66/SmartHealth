package com.ruoyi.workout.service;

import java.util.List;
import com.ruoyi.workout.domain.WorkoutExerciseDetails;

/**
 * 训练项目Service接口
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public interface IWorkoutExerciseDetailsService 
{
    /**
     * 查询训练项目
     * 
     * @param detailId 训练项目主键
     * @return 训练项目
     */
    public WorkoutExerciseDetails selectWorkoutExerciseDetailsByDetailId(Long detailId);

    /**
     * 查询训练项目列表
     * 
     * @param workoutExerciseDetails 训练项目
     * @return 训练项目集合
     */
    public List<WorkoutExerciseDetails> selectWorkoutExerciseDetailsList(WorkoutExerciseDetails workoutExerciseDetails);

    /**
     * 新增训练项目
     * 
     * @param workoutExerciseDetails 训练项目
     * @return 结果
     */
    public int insertWorkoutExerciseDetails(WorkoutExerciseDetails workoutExerciseDetails);

    /**
     * 修改训练项目
     * 
     * @param workoutExerciseDetails 训练项目
     * @return 结果
     */
    public int updateWorkoutExerciseDetails(WorkoutExerciseDetails workoutExerciseDetails);

    /**
     * 批量删除训练项目
     * 
     * @param detailIds 需要删除的训练项目主键集合
     * @return 结果
     */
    public int deleteWorkoutExerciseDetailsByDetailIds(Long[] detailIds);

    /**
     * 删除训练项目信息
     * 
     * @param detailId 训练项目主键
     * @return 结果
     */
    public int deleteWorkoutExerciseDetailsByDetailId(Long detailId);

    List<WorkoutExerciseDetails> selectByLogId(Long logId);

    /**
     * 级联删除训练项目（含 sets）
     */
    int deleteDetailWithSets(Long detailId);

}
