package com.ruoyi.food.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.food.domain.Foods;
import com.ruoyi.food.service.IFoodsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 食物查询Controller
 * 
 * @author ruoyi
 * @date 2025-12-15
 */
@RestController
@RequestMapping("/food/food")
public class FoodsController extends BaseController
{
    @Autowired
    private IFoodsService foodsService;

    /**
     * 查询食物查询列表
     */
    @PreAuthorize("@ss.hasPermi('food:food:list')")
    @GetMapping("/list")
    public TableDataInfo list(Foods foods)
    {
        startPage();
        List<Foods> list = foodsService.selectFoodsList(foods);
        return getDataTable(list);
    }

    /**
     * 导出食物查询列表
     */
    @PreAuthorize("@ss.hasPermi('food:food:export')")
    @Log(title = "食物查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Foods foods)
    {
        List<Foods> list = foodsService.selectFoodsList(foods);
        ExcelUtil<Foods> util = new ExcelUtil<Foods>(Foods.class);
        util.exportExcel(response, list, "食物查询数据");
    }

    /**
     * 获取食物查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('food:food:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(foodsService.selectFoodsById(id));
    }

    /**
     * 新增食物查询
     */
    @PreAuthorize("@ss.hasPermi('food:food:add')")
    @Log(title = "食物查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Foods foods)
    {
        return toAjax(foodsService.insertFoods(foods));
    }

    /**
     * 修改食物查询
     */
    @PreAuthorize("@ss.hasPermi('food:food:edit')")
    @Log(title = "食物查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Foods foods)
    {
        return toAjax(foodsService.updateFoods(foods));
    }

    /**
     * 删除食物查询
     */
    @PreAuthorize("@ss.hasPermi('food:food:remove')")
    @Log(title = "食物查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(foodsService.deleteFoodsByIds(ids));
    }

    @GetMapping("/group/stats")
    public AjaxResult foodGroupStats() {
        return AjaxResult.success(foodsService.selectFoodGroupStats());
    }

    @GetMapping("/detail/{id}")
    public AjaxResult getDetail(@PathVariable Long id) {
        return AjaxResult.success(foodsService.getFoodDetail(id));
    }

    @GetMapping("/detail")
    public AjaxResult detail(
            @RequestParam(required = false) Long foodId,
            @RequestParam(required = false) String foodName) {

        return AjaxResult.success(
                foodsService.getFoodBaseDetail(foodId, foodName)
        );
    }
    @GetMapping("/nutrition/daily")
    public AjaxResult getDailyActualNutrition(
            @RequestParam String date) {

//        Long userId = getUserId(); 先暂时注释掉
         Long userId =0L;
        Map<String, BigDecimal> actual =
                foodsService.calcDailyActualNutrition(userId, date);

        return AjaxResult.success(actual);
    }
}
