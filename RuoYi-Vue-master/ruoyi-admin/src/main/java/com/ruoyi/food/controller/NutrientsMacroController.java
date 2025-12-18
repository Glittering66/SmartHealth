package com.ruoyi.food.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.food.domain.NutrientsMacro;
import com.ruoyi.food.service.INutrientsMacroService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 食物营养素Controller
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
@RestController
@RequestMapping("/food/macro")
public class NutrientsMacroController extends BaseController
{
    @Autowired
    private INutrientsMacroService nutrientsMacroService;

    /**
     * 查询食物营养素列表
     */
    @PreAuthorize("@ss.hasPermi('food:macro:list')")
    @GetMapping("/list")
    public TableDataInfo list(NutrientsMacro nutrientsMacro)
    {
        startPage();
        List<NutrientsMacro> list = nutrientsMacroService.selectNutrientsMacroList(nutrientsMacro);
        return getDataTable(list);
    }

    /**
     * 导出食物营养素列表
     */
    @PreAuthorize("@ss.hasPermi('food:macro:export')")
    @Log(title = "食物营养素", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NutrientsMacro nutrientsMacro)
    {
        List<NutrientsMacro> list = nutrientsMacroService.selectNutrientsMacroList(nutrientsMacro);
        ExcelUtil<NutrientsMacro> util = new ExcelUtil<NutrientsMacro>(NutrientsMacro.class);
        util.exportExcel(response, list, "食物营养素数据");
    }

    /**
     * 获取食物营养素详细信息
     */
    @PreAuthorize("@ss.hasPermi('food:macro:query')")
    @GetMapping(value = "/{foodId}")
    public AjaxResult getInfo(@PathVariable("foodId") Long foodId)
    {
        return success(nutrientsMacroService.selectNutrientsMacroByFoodId(foodId));
    }

    /**
     * 新增食物营养素
     */
    @PreAuthorize("@ss.hasPermi('food:macro:add')")
    @Log(title = "食物营养素", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NutrientsMacro nutrientsMacro)
    {
        return toAjax(nutrientsMacroService.insertNutrientsMacro(nutrientsMacro));
    }

    /**
     * 修改食物营养素
     */
    @PreAuthorize("@ss.hasPermi('food:macro:edit')")
    @Log(title = "食物营养素", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NutrientsMacro nutrientsMacro)
    {
        return toAjax(nutrientsMacroService.updateNutrientsMacro(nutrientsMacro));
    }

    /**
     * 删除食物营养素
     */
    @PreAuthorize("@ss.hasPermi('food:macro:remove')")
    @Log(title = "食物营养素", businessType = BusinessType.DELETE)
	@DeleteMapping("/{foodIds}")
    public AjaxResult remove(@PathVariable Long[] foodIds)
    {
        return toAjax(nutrientsMacroService.deleteNutrientsMacroByFoodIds(foodIds));
    }
}
