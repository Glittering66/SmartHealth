package  com.ruoyi.food.service;

import com.ruoyi.food.domain.vo.FoodMacroStatsVO;
import com.ruoyi.food.domain.vo.FoodMacroVO;

import java.util.List;

public interface IFoodMacroService {

    List<FoodMacroVO> selectFoodMacroByGroup(String foodGroup);

    FoodMacroStatsVO selectStatsByGroup(String foodGroup);

    // 新增方法
    List<FoodMacroVO> selectFoodMacroByName(String foodName);
    FoodMacroStatsVO selectStatsByName(String foodName);

    List<FoodMacroVO> selectFoodMacroByCombo(String foodGroup, String foodName,String orderField, String orderType);
    FoodMacroStatsVO selectStatsByCombo(String foodGroup, String foodName);
}
