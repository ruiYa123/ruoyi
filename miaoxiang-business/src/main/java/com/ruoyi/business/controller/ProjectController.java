package com.ruoyi.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.queueTasks.TaskProducer;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.common.exception.UtilException;
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
import com.ruoyi.business.domain.Project;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目Controller
 *
 * @author ruoyi
 * @date 2025-01-16
 */
@RestController
@RequestMapping("/business/project")
public class ProjectController extends BaseController
{
    @Autowired
    private IProjectService projectService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private TaskProducer taskProducer;

    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('business:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(Project project)
    {
        if(!sysUserService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong) // 将字符串转换为整数
                    .collect(Collectors.toList());
            if (deptList.size() > 1) {
                project.setDept(deptList.get(1));
            } else {
                project.setDept(getDeptId());
            }
        }
        startPage();
        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:project:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(Project project)
    {
        if(!sysUserService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            if (deptList.size() > 1) {
                project.setDept(deptList.get(1));
            } else {
                project.setDept(getDeptId());
            }
        }
        List<Project> list = projectService.selectProjectList(project);
        return success(list);
    }

    /**
     * 导出项目列表
     */
    @PreAuthorize("@ss.hasPermi('business:project:export')")
    @Log(title = "项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Project project)
    {
        List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        util.exportExcel(response, list, "项目数据");
    }

    /**
     * 获取项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectService.selectProjectById(id));
    }

    /**
     * 新增项目
     */
    @PreAuthorize("@ss.hasPermi('business:project:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Project project)
    {
        project.setCreateBy(getUsername());
        String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
        List<Long> deptList = Arrays.stream(ancestors.split(","))
                .map(Long::parseLong) // 将字符串转换为整数
                .collect(Collectors.toList());
        if (deptList.size() > 1) {
            project.setDept(deptList.get(1));
        } else {
            project.setDept(getDeptId());
        }
        return toAjax(projectService.insertProject(project));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('business:project:edit')")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Project project)
    {
        project.setUpdateBy(getUsername());
        return toAjax(projectService.updateProject(project));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('business:project:remove')")
    @Log(title = "项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        List<Assignment> assignments = assignmentService.selectAssignmentByProjectIds(ids);
        List<Long> assignmentIds = new ArrayList<>();
        for (Assignment assignment : assignments) {
            if(assignment.getState() == 1) {
                throw new UtilException("任务：（" + assignment.getAssignmentName() + "）正在训练，请停止训练后再删除项目");
            } else if (assignment.getState() == 2){
                taskProducer.removeTask(assignment.getId());
            }
            assignmentIds.add(assignment.getId());
        }
        assignmentService.deleteAssignmentByIds(assignmentIds.toArray(new Long[0]));
        assignmentTrainService.deleteAssignmentTrainByAssignmentIds(assignmentIds.toArray(new Long[0]));
        return toAjax(projectService.deleteProjectByIds(ids));
    }
}
