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
import com.ruoyi.workout.domain.WorkoutExerciseDetails;
import com.ruoyi.workout.service.IWorkoutExerciseDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 训练项目Controller
 * 
 * @author ruoyi
 * @date 2025-12-16
 */
@RestController
@RequestMapping("/workout/details")
public class WorkoutExerciseDetailsController extends BaseController
{
    @Autowired
    private IWorkoutExerciseDetailsService workoutExerciseDetailsService;

    /**
     * 查询训练项目列表
     */
    @PreAuthorize("@ss.hasPermi('workout:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkoutExerciseDetails workoutExerciseDetails)
    {
        startPage();
        List<WorkoutExerciseDetails> list = workoutExerciseDetailsService.selectWorkoutExerciseDetailsList(workoutExerciseDetails);
        return getDataTable(list);
    }

    /**
     * 导出训练项目列表
     */
    @PreAuthorize("@ss.hasPermi('workout:details:export')")
    @Log(title = "训练项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkoutExerciseDetails workoutExerciseDetails)
    {
        List<WorkoutExerciseDetails> list = workoutExerciseDetailsService.selectWorkoutExerciseDetailsList(workoutExerciseDetails);
        ExcelUtil<WorkoutExerciseDetails> util = new ExcelUtil<WorkoutExerciseDetails>(WorkoutExerciseDetails.class);
        util.exportExcel(response, list, "训练项目数据");
    }

    /**
     * 获取训练项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('workout:details:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(workoutExerciseDetailsService.selectWorkoutExerciseDetailsByDetailId(detailId));
    }

    /**
     * 新增训练项目
     */
    @PreAuthorize("@ss.hasPermi('workout:details:add')")
    @Log(title = "训练项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkoutExerciseDetails workoutExerciseDetails)
    {
        return toAjax(workoutExerciseDetailsService.insertWorkoutExerciseDetails(workoutExerciseDetails));
    }

    /**
     * 修改训练项目
     */
    @PreAuthorize("@ss.hasPermi('workout:details:edit')")
    @Log(title = "训练项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkoutExerciseDetails workoutExerciseDetails)
    {
        return toAjax(workoutExerciseDetailsService.updateWorkoutExerciseDetails(workoutExerciseDetails));
    }

    /**
     * 删除训练项目
     */
    @PreAuthorize("@ss.hasPermi('workout:details:remove')")
    @Log(title = "训练项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(workoutExerciseDetailsService.deleteWorkoutExerciseDetailsByDetailIds(detailIds));
    }
}
