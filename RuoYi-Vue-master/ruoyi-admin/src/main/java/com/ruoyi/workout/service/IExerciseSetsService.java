package com.ruoyi.workout.service;

import java.util.List;
import com.ruoyi.workout.domain.ExerciseSets;

/**
 * 项目组数Service接口
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
public interface IExerciseSetsService 
{
    /**
     * 查询项目组数
     * 
     * @param setId 项目组数主键
     * @return 项目组数
     */
    public ExerciseSets selectExerciseSetsBySetId(Long setId);

    /**
     * 查询项目组数列表
     * 
     * @param exerciseSets 项目组数
     * @return 项目组数集合
     */
    public List<ExerciseSets> selectExerciseSetsList(ExerciseSets exerciseSets);

    /**
     * 新增项目组数
     * 
     * @param exerciseSets 项目组数
     * @return 结果
     */
    public int insertExerciseSets(ExerciseSets exerciseSets);

    /**
     * 修改项目组数
     * 
     * @param exerciseSets 项目组数
     * @return 结果
     */
    public int updateExerciseSets(ExerciseSets exerciseSets);

    /**
     * 批量删除项目组数
     * 
     * @param setIds 需要删除的项目组数主键集合
     * @return 结果
     */
    public int deleteExerciseSetsBySetIds(Long[] setIds);

    /**
     * 删除项目组数信息
     * 
     * @param setId 项目组数主键
     * @return 结果
     */
    public int deleteExerciseSetsBySetId(Long setId);
}
