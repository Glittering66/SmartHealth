package com.ruoyi.workout.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.workout.mapper.ExerciseMetReferenceMapper;
import com.ruoyi.workout.domain.ExerciseMetReference;
import com.ruoyi.workout.service.IExerciseMetReferenceService;

/**
 * 运动类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@Service
public class ExerciseMetReferenceServiceImpl implements IExerciseMetReferenceService 
{
    @Autowired
    private ExerciseMetReferenceMapper exerciseMetReferenceMapper;

    /**
     * 查询运动类型
     * 
     * @param exerciseId 运动类型主键
     * @return 运动类型
     */
    @Override
    public ExerciseMetReference selectExerciseMetReferenceByExerciseId(Long exerciseId)
    {
        return exerciseMetReferenceMapper.selectExerciseMetReferenceByExerciseId(exerciseId);
    }

    /**
     * 查询运动类型列表
     * 
     * @param exerciseMetReference 运动类型
     * @return 运动类型
     */
    @Override
    public List<ExerciseMetReference> selectExerciseMetReferenceList(ExerciseMetReference exerciseMetReference)
    {
        return exerciseMetReferenceMapper.selectExerciseMetReferenceList(exerciseMetReference);
    }

    /**
     * 新增运动类型
     * 
     * @param exerciseMetReference 运动类型
     * @return 结果
     */
    @Override
    public int insertExerciseMetReference(ExerciseMetReference exerciseMetReference)
    {
        return exerciseMetReferenceMapper.insertExerciseMetReference(exerciseMetReference);
    }

    /**
     * 修改运动类型
     * 
     * @param exerciseMetReference 运动类型
     * @return 结果
     */
    @Override
    public int updateExerciseMetReference(ExerciseMetReference exerciseMetReference)
    {
        return exerciseMetReferenceMapper.updateExerciseMetReference(exerciseMetReference);
    }

    /**
     * 批量删除运动类型
     * 
     * @param exerciseIds 需要删除的运动类型主键
     * @return 结果
     */
    @Override
    public int deleteExerciseMetReferenceByExerciseIds(Long[] exerciseIds)
    {
        return exerciseMetReferenceMapper.deleteExerciseMetReferenceByExerciseIds(exerciseIds);
    }

    /**
     * 删除运动类型信息
     * 
     * @param exerciseId 运动类型主键
     * @return 结果
     */
    @Override
    public int deleteExerciseMetReferenceByExerciseId(Long exerciseId)
    {
        return exerciseMetReferenceMapper.deleteExerciseMetReferenceByExerciseId(exerciseId);
    }
}
