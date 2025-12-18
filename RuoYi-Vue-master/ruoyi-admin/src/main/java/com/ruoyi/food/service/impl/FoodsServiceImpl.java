package com.ruoyi.food.service.impl;

import java.util.List;

import com.ruoyi.food.domain.dto.FoodNutritionDetailDto;
import com.ruoyi.food.domain.vo.FoodGroupStatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.food.mapper.FoodsMapper;
import com.ruoyi.food.domain.Foods;
import com.ruoyi.food.service.IFoodsService;

/**
 * 食物查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-15
 */
@Service
public class FoodsServiceImpl implements IFoodsService 
{
    @Autowired
    private FoodsMapper foodsMapper;

    /**
     * 查询食物查询
     * 
     * @param id 食物查询主键
     * @return 食物查询
     */
    @Override
    public Foods selectFoodsById(Long id)
    {
        return foodsMapper.selectFoodsById(id);
    }

    /**
     * 查询食物查询列表
     * 
     * @param foods 食物查询
     * @return 食物查询
     */
    @Override
    public List<Foods> selectFoodsList(Foods foods)
    {
        return foodsMapper.selectFoodsList(foods);
    }

    /**
     * 新增食物查询
     * 
     * @param foods 食物查询
     * @return 结果
     */
    @Override
    public int insertFoods(Foods foods)
    {
        return foodsMapper.insertFoods(foods);
    }

    /**
     * 修改食物查询
     * 
     * @param foods 食物查询
     * @return 结果
     */
    @Override
    public int updateFoods(Foods foods)
    {
        return foodsMapper.updateFoods(foods);
    }

    /**
     * 批量删除食物查询
     * 
     * @param ids 需要删除的食物查询主键
     * @return 结果
     */
    @Override
    public int deleteFoodsByIds(Long[] ids)
    {
        return foodsMapper.deleteFoodsByIds(ids);
    }

    /**
     * 删除食物查询信息
     * 
     * @param id 食物查询主键
     * @return 结果
     */
    @Override
    public int deleteFoodsById(Long id)
    {
        return foodsMapper.deleteFoodsById(id);
    }

    @Override
    public List<FoodGroupStatVO> selectFoodGroupStats() {
        return foodsMapper.selectFoodGroupStats();
    }

    @Override
    public FoodNutritionDetailDto getFoodDetail(Long foodId) {
        return foodsMapper.selectFoodNutritionDetail(foodId);
    }
}
