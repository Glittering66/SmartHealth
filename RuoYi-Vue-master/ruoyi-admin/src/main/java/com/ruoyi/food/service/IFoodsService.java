package com.ruoyi.food.service;

import java.util.List;
import com.ruoyi.food.domain.Foods;

/**
 * 食物查询Service接口
 * 
 * @author ruoyi
 * @date 2025-12-15
 */
public interface IFoodsService 
{
    /**
     * 查询食物查询
     * 
     * @param id 食物查询主键
     * @return 食物查询
     */
    public Foods selectFoodsById(Long id);

    /**
     * 查询食物查询列表
     * 
     * @param foods 食物查询
     * @return 食物查询集合
     */
    public List<Foods> selectFoodsList(Foods foods);

    /**
     * 新增食物查询
     * 
     * @param foods 食物查询
     * @return 结果
     */
    public int insertFoods(Foods foods);

    /**
     * 修改食物查询
     * 
     * @param foods 食物查询
     * @return 结果
     */
    public int updateFoods(Foods foods);

    /**
     * 批量删除食物查询
     * 
     * @param ids 需要删除的食物查询主键集合
     * @return 结果
     */
    public int deleteFoodsByIds(Long[] ids);

    /**
     * 删除食物查询信息
     * 
     * @param id 食物查询主键
     * @return 结果
     */
    public int deleteFoodsById(Long id);
}
