package  com.ruoyi.food.service;

import com.ruoyi.food.domain.vo.FoodMacroStatsVO;
import com.ruoyi.food.domain.vo.FoodMacroVO;

import java.util.List;

public interface IFoodMacroService {

    List<FoodMacroVO> selectFoodMacroByGroup(String foodGroup);

    FoodMacroStatsVO selectStatsByGroup(String foodGroup);
}
