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
}
