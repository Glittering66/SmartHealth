package com.ruoyi.food.service.impl;

import com.ruoyi.food.service.IFoodMacroService;
import com.ruoyi.food.domain.vo.FoodMacroStatsVO;
import com.ruoyi.food.domain.vo.FoodMacroVO;
import com.ruoyi.food.mapper.FoodMacroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodMacroServiceImpl implements IFoodMacroService {

    @Autowired
    private FoodMacroMapper foodMacroMapper;

    @Override
    public List<FoodMacroVO> selectFoodMacroByGroup(String foodGroup) {
        return foodMacroMapper.selectFoodMacroByGroup(foodGroup);
    }

    @Override
    public FoodMacroStatsVO selectStatsByGroup(String foodGroup) {
        return foodMacroMapper.selectStatsByGroup(foodGroup);
    }
}
