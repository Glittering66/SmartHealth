package com.ruoyi.food.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.food.domain.FoodRecord;
import com.ruoyi.food.service.IFoodRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 食物摄入记录Controller
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
@RestController
@RequestMapping("/food/record")
public class FoodRecordController extends BaseController
{
    @Autowired
    private IFoodRecordService foodRecordService;

    /**
     * 查询食物摄入记录列表
     */
    @PreAuthorize("@ss.hasPermi('food:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(FoodRecord foodRecord)
    {
        startPage();
        foodRecord.setUserId(getUserId());
        List<FoodRecord> list = foodRecordService.selectFoodRecordList(foodRecord);
       for (FoodRecord foodRecord1 : list) {
           foodRecord1.setUserId(null);
       }
        return getDataTable(list);
    }



    /**
     * 导出食物摄入记录列表
     */
    @PreAuthorize("@ss.hasPermi('food:record:export')")
    @Log(title = "食物摄入记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FoodRecord foodRecord)
    {
        foodRecord.setUserId(getUserId());
        List<FoodRecord> list = foodRecordService.selectFoodRecordList(foodRecord);
        for (FoodRecord foodRecord1 : list) {
            foodRecord1.setUserId(null);
        }
        ExcelUtil<FoodRecord> util = new ExcelUtil<FoodRecord>(FoodRecord.class);
        util.exportExcel(response, list, "食物摄入记录数据");
    }

    /**
     * 获取食物摄入记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('food:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(foodRecordService.selectFoodRecordById(id));
    }

    /**
     * 新增食物摄入记录
     */
    @PreAuthorize("@ss.hasPermi('food:record:add')")
    @Log(title = "食物摄入记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FoodRecord foodRecord)
    {
        foodRecord.setUserId(getUserId());
        return toAjax(foodRecordService.insertFoodRecord(foodRecord));

    }

    /**
     * 修改食物摄入记录
     */
    @PreAuthorize("@ss.hasPermi('food:record:edit')")
    @Log(title = "食物摄入记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FoodRecord foodRecord)
    {
        foodRecord.setUserId(getUserId());
        return toAjax(foodRecordService.updateFoodRecord(foodRecord));
    }

    /**
     * 删除食物摄入记录
     */
    @PreAuthorize("@ss.hasPermi('food:record:remove')")
    @Log(title = "食物摄入记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(foodRecordService.deleteFoodRecordByIds(ids));
    }
}
