package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.Project;
import com.ruoyi.business.domain.response.TrainDetail;
import com.ruoyi.business.queueTasks.ClientInfoManager;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
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
import com.ruoyi.business.domain.AssignmentTrain;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务训练Controller
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@RestController
@RequestMapping("/business/train")
public class AssignmentTrainController extends BaseController
{
    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ClientInfoManager clientInfoManager;

    /**
     * 查询任务训练列表
     */
    @PreAuthorize("@ss.hasPermi('business:train:list')")
    @GetMapping("/list")
    public TableDataInfo list(AssignmentTrain assignmentTrain)
    {
        startPage();
        List<AssignmentTrain> list = assignmentTrainService.selectAssignmentTrainList(assignmentTrain);
        return getDataTable(list);
    }

    /**
     * 导出任务训练列表
     */
    @PreAuthorize("@ss.hasPermi('business:train:export')")
    @Log(title = "任务训练", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssignmentTrain assignmentTrain)
    {
        List<AssignmentTrain> list = assignmentTrainService.selectAssignmentTrainList(assignmentTrain);
        ExcelUtil<AssignmentTrain> util = new ExcelUtil<AssignmentTrain>(AssignmentTrain.class);
        util.exportExcel(response, list, "任务训练数据");
    }

    /**
     * 获取任务训练详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:train:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(assignmentTrainService.selectAssignmentTrainById(id));
    }

    @PreAuthorize("@ss.hasPermi('business:train:query')")
    @GetMapping(value = "/getTrainDetail/{id}")
    public AjaxResult getTrainDetail(@PathVariable("id") Long id)
    {
        AssignmentTrain assignmentTrain = assignmentTrainService.selectAssignmentTrainById(id);
        Assignment assignment = assignmentService.selectAssignmentById(assignmentTrain.getAssignmentId());
        Project project = projectService.selectProjectById(assignment.getProjectId());
        String clientName = assignment.getClientName();
        MCGetTrainStateFeedBack.TrainState trainState =
                clientInfoManager.getClientInfo(clientName).getMcGetTrainStateFeedBack().getTrainState();
        TrainDetail trainDetail = new TrainDetail();
        trainDetail.setJsonData(
                clientInfoManager.getProcessChart(project.getProjectName(), assignment.getAssignmentName()));
        trainDetail.setTrainProcess(trainState.getTrainProcess());
        trainDetail.setProgress(trainState.getTrainPercentage());
        return success(trainDetail);
    }

    /**
     * 新增任务训练
     */
    @PreAuthorize("@ss.hasPermi('business:train:add')")
    @Log(title = "任务训练", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssignmentTrain assignmentTrain)
    {
        return toAjax(assignmentTrainService.insertAssignmentTrain(assignmentTrain));
    }

    /**
     * 修改任务训练
     */
    @PreAuthorize("@ss.hasPermi('business:train:edit')")
    @Log(title = "任务训练", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssignmentTrain assignmentTrain)
    {
        return toAjax(assignmentTrainService.updateAssignmentTrain(assignmentTrain));
    }

    /**
     * 删除任务训练
     */
    @PreAuthorize("@ss.hasPermi('business:train:remove')")
    @Log(title = "任务训练", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assignmentTrainService.deleteAssignmentTrainByIds(ids));
    }
}
