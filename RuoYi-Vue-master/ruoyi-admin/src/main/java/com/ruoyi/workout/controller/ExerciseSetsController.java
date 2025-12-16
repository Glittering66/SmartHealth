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
import com.ruoyi.workout.domain.ExerciseSets;
import com.ruoyi.workout.service.IExerciseSetsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目组数Controller
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@RestController
@RequestMapping("/workout/sets")
public class ExerciseSetsController extends BaseController
{
    @Autowired
    private IExerciseSetsService exerciseSetsService;

    /**
     * 查询项目组数列表
     */
    @PreAuthorize("@ss.hasPermi('workout:sets:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExerciseSets exerciseSets)
    {
        startPage();
        List<ExerciseSets> list = exerciseSetsService.selectExerciseSetsList(exerciseSets);
        return getDataTable(list);
    }

    /**
     * 导出项目组数列表
     */
    @PreAuthorize("@ss.hasPermi('workout:sets:export')")
    @Log(title = "项目组数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExerciseSets exerciseSets)
    {
        List<ExerciseSets> list = exerciseSetsService.selectExerciseSetsList(exerciseSets);
        ExcelUtil<ExerciseSets> util = new ExcelUtil<ExerciseSets>(ExerciseSets.class);
        util.exportExcel(response, list, "项目组数数据");
    }

    /**
     * 获取项目组数详细信息
     */
    @PreAuthorize("@ss.hasPermi('workout:sets:query')")
    @GetMapping(value = "/{setId}")
    public AjaxResult getInfo(@PathVariable("setId") Long setId)
    {
        return success(exerciseSetsService.selectExerciseSetsBySetId(setId));
    }

    /**
     * 新增项目组数
     */
    @PreAuthorize("@ss.hasPermi('workout:sets:add')")
    @Log(title = "项目组数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExerciseSets exerciseSets)
    {
        return toAjax(exerciseSetsService.insertExerciseSets(exerciseSets));
    }

    /**
     * 修改项目组数
     */
    @PreAuthorize("@ss.hasPermi('workout:sets:edit')")
    @Log(title = "项目组数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExerciseSets exerciseSets)
    {
        return toAjax(exerciseSetsService.updateExerciseSets(exerciseSets));
    }

    /**
     * 删除项目组数
     */
    @PreAuthorize("@ss.hasPermi('workout:sets:remove')")
    @Log(title = "项目组数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{setIds}")
    public AjaxResult remove(@PathVariable Long[] setIds)
    {
        return toAjax(exerciseSetsService.deleteExerciseSetsBySetIds(setIds));
    }
}
