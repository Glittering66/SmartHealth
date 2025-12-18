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
import com.ruoyi.food.domain.UserNutritionProfile;
import com.ruoyi.food.service.IUserNutritionProfileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户营养计算基础信息Controller
 * 
 * @author ruoyi
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/food/profile")
public class UserNutritionProfileController extends BaseController
{
    @Autowired
    private IUserNutritionProfileService userNutritionProfileService;

    /**
     * 查询用户营养计算基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('food:profile:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserNutritionProfile userNutritionProfile)
    {
        startPage();
        List<UserNutritionProfile> list = userNutritionProfileService.selectUserNutritionProfileList(userNutritionProfile);
        return getDataTable(list);
    }

    /**
     * 导出用户营养计算基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('food:profile:export')")
    @Log(title = "用户营养计算基础信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserNutritionProfile userNutritionProfile)
    {
        List<UserNutritionProfile> list = userNutritionProfileService.selectUserNutritionProfileList(userNutritionProfile);
        ExcelUtil<UserNutritionProfile> util = new ExcelUtil<UserNutritionProfile>(UserNutritionProfile.class);
        util.exportExcel(response, list, "用户营养计算基础信息数据");
    }

    /**
     * 获取用户营养计算基础信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('food:profile:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userNutritionProfileService.selectUserNutritionProfileById(id));
    }

    /**
     * 新增用户营养计算基础信息
     */
    @PreAuthorize("@ss.hasPermi('food:profile:add')")
    @Log(title = "用户营养计算基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserNutritionProfile userNutritionProfile)
    {
        return toAjax(userNutritionProfileService.insertUserNutritionProfile(userNutritionProfile));
    }

    /**
     * 修改用户营养计算基础信息
     */
    @PreAuthorize("@ss.hasPermi('food:profile:edit')")
    @Log(title = "用户营养计算基础信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserNutritionProfile userNutritionProfile)
    {
        return toAjax(userNutritionProfileService.updateUserNutritionProfile(userNutritionProfile));
    }

    /**
     * 删除用户营养计算基础信息
     */
    @PreAuthorize("@ss.hasPermi('food:profile:remove')")
    @Log(title = "用户营养计算基础信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userNutritionProfileService.deleteUserNutritionProfileByIds(ids));
    }
}
