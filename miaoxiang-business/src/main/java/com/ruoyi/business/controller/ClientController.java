package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.request.MessageRequest;
import com.ruoyi.business.socket.SocketService;
import com.ruoyi.business.socket.messageHandler.handler.command.TestCommandHandler;
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
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.service.IClientService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户端Controller
 *
 * @author ruoyi
 * @date 2025-02-11
 */
@RestController
@RequestMapping("/business/client")
public class ClientController extends BaseController
{
    @Autowired
    private IClientService clientService;

    @Autowired
    private TestCommandHandler testCommandHandler;

    @Autowired
    private SocketService socketService;
    /**
     * 查询客户端列表
     */
    @PreAuthorize("@ss.hasPermi('business:client:list')")
    @GetMapping("/list")
    public TableDataInfo list(Client client)
    {
        startPage();
        List<Client> list = clientService.selectClientList(client);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:client:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll()
    {
        List<Client> list = clientService.selectClientList(new Client());
        return success(list);
    }

    /**
     * 导出客户端列表
     */
    @PreAuthorize("@ss.hasPermi('business:client:export')")
    @Log(title = "客户端", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Client client)
    {
        List<Client> list = clientService.selectClientList(client);
        ExcelUtil<Client> util = new ExcelUtil<Client>(Client.class);
        util.exportExcel(response, list, "客户端数据");
    }

    /**
     * 获取客户端详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:client:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(clientService.selectClientById(id));
    }

    @PreAuthorize("@ss.hasPermi('business:client:query')")
    @GetMapping(value = "/status/{id}")
    public AjaxResult getStatus(@PathVariable("id") Long id)
    {
        Client client = clientService.selectClientById(id);
        return success(socketService.getClientStatus(client.getIp(), client.getPort()));
    }

    /**
     * 新增客户端
     */
    @PreAuthorize("@ss.hasPermi('business:client:add')")
    @Log(title = "客户端", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Client client)
    {
        return toAjax(clientService.insertClient(client));
    }

    @PreAuthorize("@ss.hasPermi('business:client:add')")
    @Log(title = "客户端", businessType = BusinessType.INSERT)
    @PostMapping("/sendMessage")
    public AjaxResult sendMessage(@RequestBody MessageRequest messageRequest)
    {
        ;
        return toAjax(testCommandHandler.testCommand(messageRequest));
    }

    /**
     * 修改客户端
     */
    @PreAuthorize("@ss.hasPermi('business:client:edit')")
    @Log(title = "客户端", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Client client)
    {
        return toAjax(clientService.updateClient(client));
    }

    /**
     * 删除客户端
     */
    @PreAuthorize("@ss.hasPermi('business:client:remove')")
    @Log(title = "客户端", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clientService.deleteClientByIds(ids));
    }
}
