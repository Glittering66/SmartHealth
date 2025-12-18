package com.ruoyi.food.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.food.domain.dto.*;
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

    public FoodBaseDetailDto getFoodBaseDetail(Long foodId, String foodName) {
        return foodsMapper.selectFoodBaseDetail(foodId, foodName);
    }
    private BigDecimal toBigDecimal(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof Float || value instanceof Double) {
            // ❗ 一定用 String 构造，不能 new BigDecimal(double)
            return new BigDecimal(String.valueOf(value));
        }

        if (value instanceof Integer || value instanceof Long) {
            return BigDecimal.valueOf(((Number) value).longValue());
        }

        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }

        throw new IllegalArgumentException(
                "Unsupported numeric type: " + value.getClass()
        );
    }
    /**
     * 累加营养素（支持 Float / Double / BigDecimal）
     *
     * @param sum   汇总 Map
     * @param key   营养素 key
     * @param value DTO 中的营养值（Float / Double / BigDecimal）
     * @param ratio 摄入比例 = totalWeightG / 100
     */

    private void add(Map<String, BigDecimal> sum,
                     String key,
                     Object value,
                     BigDecimal ratio) {

        if (value == null || ratio == null) {
            return;
        }

        BigDecimal base = toBigDecimal(value);
        if (base == null) {
            return;
        }

        BigDecimal actual = base.multiply(ratio);

        sum.merge(key, actual, BigDecimal::add);
    }
    private BigDecimal bd(Float v) {
        return v == null ? BigDecimal.ZERO : BigDecimal.valueOf(v);
    }

    public Map<String, BigDecimal> calcDailyActualNutrition(
            Long userId, String date) {

        List<FoodNutritionDetailDto> list =
                foodsMapper.selectFoodNutritionDetailByDate(userId, date);

        Map<String, BigDecimal> sum = new HashMap<>();

        for (FoodNutritionDetailDto dto : list) {

            BigDecimal ratio = dto.getTotalWeightG()
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

            MacroNutritionDto m = dto.getMacro();
            if (m != null) {
                add(sum, "energy", m.getCalories(), ratio);
                add(sum, "protein", m.getProtein(), ratio);
                add(sum, "fat", m.getFat(), ratio);
                add(sum, "carb", m.getCarbohydrate(), ratio);
                add(sum, "fiber", m.getFiber(), ratio);
            }

            VitaminDto v = dto.getVitamins();
            if (v != null) {
                add(sum, "vitC", v.getVitaminC(), ratio);
                add(sum, "vitD", v.getVitaminD(), ratio);
            }

            MineralDto mi = dto.getMinerals();
            if (mi != null) {
                add(sum, "ca", mi.getCalcium(), ratio);
                add(sum, "fe", mi.getIron(), ratio);
            }

            FattyAcidDto fa = dto.getFattyAcids();
            if (fa != null) {
                add(sum, "omega3", bd(fa.getOmega3()), ratio);
                add(sum, "epaDha", bd(fa.getEpa()), ratio);
                add(sum, "epaDha", bd(fa.getDha()), ratio);
            }
        }

        return sum;
    }

}
