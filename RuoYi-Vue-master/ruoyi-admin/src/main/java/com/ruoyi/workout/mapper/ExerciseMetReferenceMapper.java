package com.ruoyi.workout.mapper;

import java.util.List;
import com.ruoyi.workout.domain.ExerciseMetReference;

/**
 * 运动类型Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public interface ExerciseMetReferenceMapper 
{
    /**
     * 查询运动类型
     * 
     * @param exerciseId 运动类型主键
     * @return 运动类型
     */
    public ExerciseMetReference selectExerciseMetReferenceByExerciseId(Long exerciseId);

    /**
     * 查询运动类型列表
     * 
     * @param exerciseMetReference 运动类型
     * @return 运动类型集合
     */
    public List<ExerciseMetReference> selectExerciseMetReferenceList(ExerciseMetReference exerciseMetReference);

    /**
     * 新增运动类型
     * 
     * @param exerciseMetReference 运动类型
     * @return 结果
     */
    public int insertExerciseMetReference(ExerciseMetReference exerciseMetReference);

    /**
     * 修改运动类型
     * 
     * @param exerciseMetReference 运动类型
     * @return 结果
     */
    public int updateExerciseMetReference(ExerciseMetReference exerciseMetReference);

    /**
     * 删除运动类型
     * 
     * @param exerciseId 运动类型主键
     * @return 结果
     */
    public int deleteExerciseMetReferenceByExerciseId(Long exerciseId);

    /**
     * 批量删除运动类型
     * 
     * @param exerciseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExerciseMetReferenceByExerciseIds(Long[] exerciseIds);
}
