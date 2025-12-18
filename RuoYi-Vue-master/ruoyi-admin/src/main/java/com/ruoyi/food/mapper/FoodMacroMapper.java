package com.ruoyi.food.mapper;

import com.ruoyi.food.domain.vo.FoodMacroStatsVO;
import com.ruoyi.food.domain.vo.FoodMacroVO;

import java.util.List;

public interface FoodMacroMapper {

    /**
     * 按分类查询食物宏量营养素（联表）
     */
    List<FoodMacroVO> selectFoodMacroByGroup(String foodGroup);

    /**
     * 分类统计信息
     */
    FoodMacroStatsVO selectStatsByGroup(String foodGroup);
}
