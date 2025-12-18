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
    @Override
    public
        // 新增方法
    List<FoodMacroVO> selectFoodMacroByName(String foodName){
        return foodMacroMapper.selectFoodMacroByName(foodName);
    }
    @Override
    public FoodMacroStatsVO selectStatsByName(String foodName){
        return foodMacroMapper.selectStatsByName(foodName);
    }
    @Override
    public  List<FoodMacroVO> selectFoodMacroByCombo(String foodGroup, String foodName,String orderField, String orderType){
        return foodMacroMapper.selectFoodMacroByCombo(foodGroup, foodName,orderField,  orderType);
    }
    @Override
    public FoodMacroStatsVO selectStatsByCombo(String foodGroup, String foodName){
        return foodMacroMapper.selectStatsByCombo(foodGroup, foodName);
    }

}
