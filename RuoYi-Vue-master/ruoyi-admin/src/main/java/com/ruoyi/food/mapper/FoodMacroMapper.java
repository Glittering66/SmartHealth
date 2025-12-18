package com.ruoyi.food.mapper;

import com.ruoyi.food.domain.vo.FoodMacroStatsVO;
import com.ruoyi.food.domain.vo.FoodMacroVO;
import org.apache.ibatis.annotations.Param;

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

    List<FoodMacroVO> selectFoodMacroByName(String foodName);

    FoodMacroStatsVO selectStatsByName(String foodName);

    List<FoodMacroVO> selectFoodMacroByCombo(  @Param("param1") String foodGroup,
                                               @Param("param2") String foodName,
                                               @Param("orderField") String orderField,
                                               @Param("orderType") String orderType);

    FoodMacroStatsVO selectStatsByCombo(String foodGroup, String foodName);
}
