package com.ruoyi.business.controller;

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
import com.ruoyi.business.domain.TrainLog;
import com.ruoyi.business.service.ITrainLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 训练日志Controller
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@RestController
@RequestMapping("/business/trainLog")
public class TrainLogController extends BaseController
{
    @Autowired
    private ITrainLogService trainLogService;

    /**
     * 查询训练日志列表
     */
    @PreAuthorize("@ss.hasPermi('business:trainLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(TrainLog trainLog)
    {
        startPage();
        List<TrainLog> list = trainLogService.selectTrainLogList(trainLog);
        return getDataTable(list);
    }

    /**
     * 导出训练日志列表
     */
    @PreAuthorize("@ss.hasPermi('business:trainLog:export')")
    @Log(title = "训练日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TrainLog trainLog)
    {
        List<TrainLog> list = trainLogService.selectTrainLogList(trainLog);
        ExcelUtil<TrainLog> util = new ExcelUtil<TrainLog>(TrainLog.class);
        util.exportExcel(response, list, "训练日志数据");
    }

    /**
     * 获取训练日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:trainLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(trainLogService.selectTrainLogById(id));
    }

    /**
     * 新增训练日志
     */
    @PreAuthorize("@ss.hasPermi('business:trainLog:add')")
    @Log(title = "训练日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TrainLog trainLog)
    {
        return toAjax(trainLogService.insertTrainLog(trainLog));
    }

    /**
     * 修改训练日志
     */
    @PreAuthorize("@ss.hasPermi('business:trainLog:edit')")
    @Log(title = "训练日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrainLog trainLog)
    {
        return toAjax(trainLogService.updateTrainLog(trainLog));
    }

    /**
     * 删除训练日志
     */
    @PreAuthorize("@ss.hasPermi('business:trainLog:remove')")
    @Log(title = "训练日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(trainLogService.deleteTrainLogByIds(ids));
    }
}
