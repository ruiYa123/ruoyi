package com.ruoyi.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.queueTasks.TaskConsumer;
import com.ruoyi.business.queueTasks.TaskProducer;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务Controller
 *
 * @author ruoyi
 * @date 2025-01-16
 */
@RestController
@RequestMapping("/business/assignment")
public class AssignmentController extends BaseController
{
    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;
    @Autowired
    private TaskProducer taskProducer;

    /**
     * 查询任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Assignment assignment)
    {
        if(!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong) // 将字符串转换为整数
                    .collect(Collectors.toList());
            if (deptList.size() > 1) {
                assignment.setDept(deptList.get(1));
            } else {
                assignment.setDept(getDeptId());
            }
        }
        startPage();
        List<Assignment> list = assignmentService.selectAssignmentList(assignment);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(Assignment assignment)
    {
        List<Assignment> list = assignmentService.selectAssignmentList(assignment);
        Collections.reverse(list);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/counts")
    public AjaxResult getCounts()
    {
        Assignment assignment = new Assignment();
        if(!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong) // 将字符串转换为整数
                    .collect(Collectors.toList());
            if (deptList.size() > 1) {
                assignment.setDept(deptList.get(1));
            } else {
                assignment.setDept(getDeptId());
            }
        }
        return success(assignmentService.getStateCounts(assignment));
    }

    /**
     * 导出任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:export')")
    @Log(title = "任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Assignment assignment)
    {
        List<Assignment> list = assignmentService.selectAssignmentList(assignment);
        ExcelUtil<Assignment> util = new ExcelUtil<Assignment>(Assignment.class);
        util.exportExcel(response, list, "任务数据");
    }

    /**
     * 获取任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(assignmentService.selectAssignmentById(id));
    }

    /**
     * 新增任务
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:add')")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Assignment assignment)
    {
        assignment.setCreateBy(getUsername());
        String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
        List<Long> deptList = Arrays.stream(ancestors.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        if (deptList.size() > 1) {
            assignment.setDept(deptList.get(1));
        } else {
            assignment.setDept(getDeptId());
        }

        return toAjax(assignmentService.insertAssignment(assignment));
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:query')")
    @GetMapping(value = "/start/{id}")
    public AjaxResult startAssignment(@PathVariable("id") Long id)
    {
        Assignment assignment = assignmentService.selectAssignmentById(id);
        taskProducer.addTask(assignment.getAssignmentName(), false);
        return success();
    }

    /**
     *
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:edit')")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Assignment assignment)
    {
        assignment.setUpdateBy(getUsername());
        return toAjax(assignmentService.updateAssignment(assignment));
    }

    /**
     * 删除任务
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:remove')")
    @Log(title = "任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assignmentService.deleteAssignmentByIds(ids));
    }
}
