package com.ruoyi.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
import com.ruoyi.business.domain.AssignmentTrain;
import com.ruoyi.business.domain.Project;
import com.ruoyi.business.domain.response.TrainingAssignment;
import com.ruoyi.business.queueTasks.ClientInfoManager;
import com.ruoyi.business.queueTasks.TaskProducer;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.socket.messageHandler.handler.command.MCChangeTrainParamCommandHandler;
import com.ruoyi.business.socket.messageHandler.handler.command.MCStopTrainCommandHandler;

import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
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
    private IProjectService projectService;
    @Autowired
    private TaskProducer taskProducer;

    @Autowired
    private MCChangeTrainParamCommandHandler mcChangeTrainParamCommandHandler;

    @Autowired
    private MCStopTrainCommandHandler mcStopTrainCommandHandler;

    @Autowired
    private ClientInfoManager clientInfoManager;


    private Long getDept() {
        if(!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong) // 将字符串转换为整数
                    .collect(Collectors.toList());
            if (deptList.size() > 1) {
                return deptList.get(1);
            } else {
                return getDeptId();
            }
        }
        return null;
    }
    /**
     * 查询任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Assignment assignment)
    {
        if (!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            assignment.setDept(getDept());
        }
        startPage();
        List<Assignment> list;
        if (assignment.getState() == 2) {
            Long[] tasks = taskProducer.getTask();
            list = assignmentService.selectAssignmentListByIds(assignment, tasks);
        } else {
            list = assignmentService.selectAssignmentList(assignment);
        }
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(Assignment assignment)
    {
        if (!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            assignment.setDept(getDept());
        }
        List<Assignment> list = assignmentService.selectAssignmentList(assignment);
        Collections.reverse(list);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/progress/{projectName}/{assignmentName}")
    public AjaxResult getProgress(@PathVariable("projectName") String projecname,
                                  @PathVariable("assignmentName") String assignmentName)
    {

        return success(clientInfoManager.getProcessChart(projecname,assignmentName));
    }
    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/progressState/{clientName}")
    public AjaxResult getProgressState(@PathVariable("clientName") String clientName)
    {

        return success(clientInfoManager.getClientInfo(clientName)
                .getMcGetClientStateFeedBack().getClientState().getTrainState());
    }


    @PreAuthorize("@ss.hasPermi('business:assignment:insert')")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @GetMapping("/resetRedisAssignment")
    public AjaxResult resetRedisAssignment() {

        if (userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            return success(taskProducer.checkAndUpdateTasks());
        }
        return error("校准接口，无权访问");
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/listTraining")
    public AjaxResult listTraining()
    {
        Assignment assignment = new Assignment();
//        assignment.setState(1);
        if (!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            assignment.setDept(getDept());
        }
        List<Assignment> list = assignmentService.selectAssignmentList(assignment);
        List<TrainingAssignment> response = new ArrayList<>();
        list.forEach(e -> {
            TrainingAssignment t = new TrainingAssignment();
            if (e.getState() == 1) {
                AssignmentTrain assignmentTrain = new AssignmentTrain();
                assignmentTrain.setAssignmentId(e.getId());
                PageHelper.startPage(1, 1, "create_time desc");
                List<AssignmentTrain> assignmentTrains = assignmentTrainService.selectAssignmentTrainList(assignmentTrain);
                if (assignmentTrains.size() > 0) {
                    t.setProgress(assignmentTrains.get(0).getProgress());
                }
            }
            BeanUtils.copyProperties(e, t);

            response.add(t);
        });
        return success(response);
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:list')")
    @GetMapping("/counts")
    public AjaxResult getCounts()
    {
        Assignment assignment = new Assignment();
        if(!userService.selectUserRoleGroup(getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong)
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
        assignment.setState(3);
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
        assignment.setState(2);
        assignment.setUpdateBy(getUsername());
        taskProducer.addTask(assignment.getId(), false);
        return success(assignmentService.updateAssignment(assignment));
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:query')")
    @GetMapping(value = "/startPrioritize/{id}")
    public AjaxResult startPrioritizeAssignment(@PathVariable("id") Long id)
    {
        Assignment assignment = assignmentService.selectAssignmentById(id);
        assignment.setState(2);
        assignment.setUpdateBy(getUsername());
        assignment.setUpdateTime(DateUtils.getNowDate());
        taskProducer.addTask(assignment.getId(), true);
        return success(assignmentService.updateAssignment(assignment));
    }

    @PreAuthorize("@ss.hasPermi('business:assignment:query')")
    @GetMapping(value = "/stop/{id}")
    public AjaxResult stopAssignment(@PathVariable("id") Long id)
    {
        Assignment assignment = assignmentService.selectAssignmentById(id);
        Integer currentState = assignment.getState();
        assignment.setState(3);
        assignment.setUpdateBy(getUsername());
        assignment.setUpdateTime(DateUtils.getNowDate());
        if (currentState == 2) {
            taskProducer.removeTask(assignment.getId());
        } else if (currentState == 1){
            mcStopTrainCommandHandler.stopTrain(assignment.getClientName());

        } else if (currentState == 0){
            throw new UtilException(assignment.getAssignmentName() + "任务已训练完成");
        }
        return success(assignmentService.updateAssignment(assignment));
    }

    /**
     *
     */
    @PreAuthorize("@ss.hasPermi('business:assignment:edit')")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Assignment assignment)
    {
        Assignment assignment1 = assignmentService.selectAssignmentById(assignment.getId());
        if (assignment1 != null && assignment1.getState() == 1) {
            Project project = projectService.selectProjectById(assignment1.getProjectId());
            mcChangeTrainParamCommandHandler.changeTrainParam(
                    project.getProjectName(),
                    assignment,
                    assignment1.getClientName());
        }
        assignment.setUpdateBy(getUsername());
        assignment.setUpdateTime(DateUtils.getNowDate());
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
        Arrays.stream(ids).forEach( id -> taskProducer.removeTask(id));

        int deleteCount = assignmentService.deleteAssignmentByIds(ids);
        assignmentTrainService.deleteAssignmentTrainByAssignmentIds(ids);
        return toAjax(deleteCount);
    }
}
