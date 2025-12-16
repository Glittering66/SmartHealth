package com.ruoyi.workout.controller;

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
import com.ruoyi.workout.domain.ExerciseMetReference;
import com.ruoyi.workout.service.IExerciseMetReferenceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 运动类型Controller
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@RestController
@RequestMapping("/workout/reference")
public class ExerciseMetReferenceController extends BaseController
{
    @Autowired
    private IExerciseMetReferenceService exerciseMetReferenceService;

    /**
     * 查询运动类型列表
     */
    @PreAuthorize("@ss.hasPermi('workout:reference:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExerciseMetReference exerciseMetReference)
    {
        startPage();
        List<ExerciseMetReference> list = exerciseMetReferenceService.selectExerciseMetReferenceList(exerciseMetReference);
        return getDataTable(list);
    }

    /**
     * 导出运动类型列表
     */
    @PreAuthorize("@ss.hasPermi('workout:reference:export')")
    @Log(title = "运动类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExerciseMetReference exerciseMetReference)
    {
        List<ExerciseMetReference> list = exerciseMetReferenceService.selectExerciseMetReferenceList(exerciseMetReference);
        ExcelUtil<ExerciseMetReference> util = new ExcelUtil<ExerciseMetReference>(ExerciseMetReference.class);
        util.exportExcel(response, list, "运动类型数据");
    }

    /**
     * 获取运动类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('workout:reference:query')")
    @GetMapping(value = "/{exerciseId}")
    public AjaxResult getInfo(@PathVariable("exerciseId") Long exerciseId)
    {
        return success(exerciseMetReferenceService.selectExerciseMetReferenceByExerciseId(exerciseId));
    }

    /**
     * 新增运动类型
     */
    @PreAuthorize("@ss.hasPermi('workout:reference:add')")
    @Log(title = "运动类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExerciseMetReference exerciseMetReference)
    {
        return toAjax(exerciseMetReferenceService.insertExerciseMetReference(exerciseMetReference));
    }

    /**
     * 修改运动类型
     */
    @PreAuthorize("@ss.hasPermi('workout:reference:edit')")
    @Log(title = "运动类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExerciseMetReference exerciseMetReference)
    {
        return toAjax(exerciseMetReferenceService.updateExerciseMetReference(exerciseMetReference));
    }

    /**
     * 删除运动类型
     */
    @PreAuthorize("@ss.hasPermi('workout:reference:remove')")
    @Log(title = "运动类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{exerciseIds}")
    public AjaxResult remove(@PathVariable Long[] exerciseIds)
    {
        return toAjax(exerciseMetReferenceService.deleteExerciseMetReferenceByExerciseIds(exerciseIds));
    }
}
