package com.ruoyi.food.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.food.domain.vo.FoodMacroStatsVO;
import com.ruoyi.food.domain.vo.FoodMacroVO;
import com.ruoyi.food.service.IFoodMacroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food/macro")
public class FoodMacroController extends BaseController {

    @Autowired
    private IFoodMacroService foodMacroService;

    /**
     * 按分类查询（联表 + 统计）
     */
    @GetMapping("/group/list")
    public AjaxResult listByGroup(@RequestParam String foodGroup) {

        startPage();
        List<FoodMacroVO> list = foodMacroService.selectFoodMacroByGroup(foodGroup);

        FoodMacroStatsVO stats = foodMacroService.selectStatsByGroup(foodGroup);

        return AjaxResult.success()
                .put("rows", list)
                .put("total", getDataTable(list).getTotal())
                .put("stats", stats);
    }
    @GetMapping("/name/list")
    public AjaxResult listByName(@RequestParam String foodName) {
        startPage();
        List<FoodMacroVO> list = foodMacroService.selectFoodMacroByName(foodName);
        FoodMacroStatsVO stats = foodMacroService.selectStatsByName(foodName);

        return AjaxResult.success()
                .put("rows", list)
                .put("total", getDataTable(list).getTotal())
                .put("stats", stats);
    }

    /**
     * 按分类和名称组合查询
     */
    @GetMapping("/combo/list")
    public AjaxResult listByCombo(@RequestParam(required = false) String foodGroup,
                                  @RequestParam(required = false) String foodName,
                                  @RequestParam(required = false, defaultValue = "") String orderField,
                                    @RequestParam(required = false, defaultValue = "") String  orderType) {
        startPage();
        List<FoodMacroVO> list = foodMacroService.selectFoodMacroByCombo(foodGroup, foodName, orderField,  orderType);
        FoodMacroStatsVO stats = foodMacroService.selectStatsByCombo(foodGroup, foodName);

        return AjaxResult.success()
                .put("rows", list)
                .put("total", getDataTable(list).getTotal())
                .put("stats", stats);
    }

}
