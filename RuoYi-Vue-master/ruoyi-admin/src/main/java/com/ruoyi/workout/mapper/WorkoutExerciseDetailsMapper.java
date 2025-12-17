package com.ruoyi.workout.mapper;

import java.util.List;
import com.ruoyi.workout.domain.WorkoutExerciseDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * 训练项目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@Mapper
public interface WorkoutExerciseDetailsMapper 
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
     * 删除训练项目
     * 
     * @param detailId 训练项目主键
     * @return 结果
     */
    public int deleteWorkoutExerciseDetailsByDetailId(Long detailId);

    /**
     * 批量删除训练项目
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkoutExerciseDetailsByDetailIds(Long[] detailIds);

    public int deleteByLogId(Long logId);
}
