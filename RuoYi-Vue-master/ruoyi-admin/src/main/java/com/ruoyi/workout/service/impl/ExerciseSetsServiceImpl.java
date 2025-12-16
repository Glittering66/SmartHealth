package com.ruoyi.workout.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.workout.mapper.ExerciseSetsMapper;
import com.ruoyi.workout.domain.ExerciseSets;
import com.ruoyi.workout.service.IExerciseSetsService;

/**
 * 项目组数Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@Service
public class ExerciseSetsServiceImpl implements IExerciseSetsService 
{
    @Autowired
    private ExerciseSetsMapper exerciseSetsMapper;

    /**
     * 查询项目组数
     * 
     * @param setId 项目组数主键
     * @return 项目组数
     */
    @Override
    public ExerciseSets selectExerciseSetsBySetId(Long setId)
    {
        return exerciseSetsMapper.selectExerciseSetsBySetId(setId);
    }

    /**
     * 查询项目组数列表
     * 
     * @param exerciseSets 项目组数
     * @return 项目组数
     */
    @Override
    public List<ExerciseSets> selectExerciseSetsList(ExerciseSets exerciseSets)
    {
        return exerciseSetsMapper.selectExerciseSetsList(exerciseSets);
    }

    /**
     * 新增项目组数
     * 
     * @param exerciseSets 项目组数
     * @return 结果
     */
    @Override
    public int insertExerciseSets(ExerciseSets exerciseSets)
    {
        return exerciseSetsMapper.insertExerciseSets(exerciseSets);
    }

    /**
     * 修改项目组数
     * 
     * @param exerciseSets 项目组数
     * @return 结果
     */
    @Override
    public int updateExerciseSets(ExerciseSets exerciseSets)
    {
        return exerciseSetsMapper.updateExerciseSets(exerciseSets);
    }

    /**
     * 批量删除项目组数
     * 
     * @param setIds 需要删除的项目组数主键
     * @return 结果
     */
    @Override
    public int deleteExerciseSetsBySetIds(Long[] setIds)
    {
        return exerciseSetsMapper.deleteExerciseSetsBySetIds(setIds);
    }

    /**
     * 删除项目组数信息
     * 
     * @param setId 项目组数主键
     * @return 结果
     */
    @Override
    public int deleteExerciseSetsBySetId(Long setId)
    {
        return exerciseSetsMapper.deleteExerciseSetsBySetId(setId);
    }
}
