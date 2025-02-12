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
import com.ruoyi.business.domain.ClientLog;
import com.ruoyi.business.service.IClientLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户端操作日志Controller
 *
 * @author ruoyi
 * @date 2025-02-11
 */
@RestController
@RequestMapping("/business/clientLog")
public class ClientLogController extends BaseController
{
    @Autowired
    private IClientLogService clientLogService;

    /**
     * 查询客户端操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('business:clientLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClientLog clientLog)
    {
        startPage();
        List<ClientLog> list = clientLogService.selectClientLogList(clientLog);
        return getDataTable(list);
    }

    /**
     * 导出客户端操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('business:clientLog:export')")
    @Log(title = "客户端操作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClientLog clientLog)
    {
        List<ClientLog> list = clientLogService.selectClientLogList(clientLog);
        ExcelUtil<ClientLog> util = new ExcelUtil<ClientLog>(ClientLog.class);
        util.exportExcel(response, list, "客户端操作日志数据");
    }

    /**
     * 获取客户端操作日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:clientLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(clientLogService.selectClientLogById(id));
    }

    /**
     * 新增客户端操作日志
     */
    @PreAuthorize("@ss.hasPermi('business:clientLog:add')")
    @Log(title = "客户端操作日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClientLog clientLog)
    {
        return toAjax(clientLogService.insertClientLog(clientLog));
    }

    /**
     * 修改客户端操作日志
     */
    @PreAuthorize("@ss.hasPermi('business:clientLog:edit')")
    @Log(title = "客户端操作日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClientLog clientLog)
    {
        return toAjax(clientLogService.updateClientLog(clientLog));
    }

    /**
     * 删除客户端操作日志
     */
    @PreAuthorize("@ss.hasPermi('business:clientLog:remove')")
    @Log(title = "客户端操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clientLogService.deleteClientLogByIds(ids));
    }
}
