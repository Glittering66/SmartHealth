package com.ruoyi.workout.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.workout.domain.WorkoutLogs;
import com.ruoyi.workout.service.IWorkoutLogsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 运动记录Controller
 *
 * @author ruoyi
 * @date 2025-12-16
 */
@RestController
@RequestMapping("/workout/logs")
public class WorkoutLogsController extends BaseController
{
    @Autowired
    private IWorkoutLogsService workoutLogsService;

    /**
     * 查询运动记录列表
     */
    @PreAuthorize("@ss.hasPermi('workout:logs:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkoutLogs workoutLogs)
    {
        startPage();
        List<WorkoutLogs> list = workoutLogsService.selectWorkoutLogsList(workoutLogs);
        return getDataTable(list);
    }

    /**
     * 导出运动记录列表
     */
    @PreAuthorize("@ss.hasPermi('workout:logs:export')")
    @Log(title = "运动记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkoutLogs workoutLogs)
    {
        List<WorkoutLogs> list = workoutLogsService.selectWorkoutLogsList(workoutLogs);
        ExcelUtil<WorkoutLogs> util = new ExcelUtil<WorkoutLogs>(WorkoutLogs.class);
        util.exportExcel(response, list, "运动记录数据");
    }

    /**
     * 获取运动记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('workout:logs:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId)
    {
        return success(workoutLogsService.selectWorkoutLogsByLogId(logId));
    }

    /**
     * 新增运动记录
     */
    @PreAuthorize("@ss.hasPermi('workout:logs:add')")
    @Log(title = "运动记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkoutLogs workoutLogs)
    {
        // 1️⃣ 用户ID：从登录态取
        workoutLogs.setUserId(SecurityUtils.getUserId());

        // 2️⃣ 运动日期：兜底处理（关键）
        if (workoutLogs.getWorkoutDate() == null) {
            workoutLogs.setWorkoutDate(new Date());
        }

        return toAjax(workoutLogsService.insertWorkoutLogs(workoutLogs));
    }



    /**
     * 修改运动记录
     */
    @PreAuthorize("@ss.hasPermi('workout:logs:edit')")
    @Log(title = "运动记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkoutLogs workoutLogs)
    {
        return toAjax(workoutLogsService.updateWorkoutLogs(workoutLogs));
    }

    /**
     * 删除运动记录
     */

    @DeleteMapping("/{logId}")
    public AjaxResult deleteWorkoutWithAll(@PathVariable Long logId) {
        return toAjax(workoutLogsService.deleteWorkoutWithAll(logId));
    }

}
