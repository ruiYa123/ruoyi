package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.request.MessageRequest;
import com.ruoyi.business.queueTasks.ClientInfoManager;
import com.ruoyi.business.socket.messageHandler.handler.command.MCStopTrainCommandHandler;
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

import static com.ruoyi.business.domain.Client.StateEnum.ACTIVATE;
import static com.ruoyi.business.domain.Client.StateEnum.DEACTIVATE;

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
    private ClientInfoManager clientInfoManager;

    @Autowired
    private MCStopTrainCommandHandler mcStopTrainCommandHandler;
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
    @GetMapping(value = "/status/{name}")
    public AjaxResult getStatus(@PathVariable("name") String name)
    {
        return success(clientInfoManager.getClientInfo(name));
    }

    @PreAuthorize("@ss.hasPermi('business:client:query')")
    @GetMapping(value = "/stopTrain/{name}")
    public AjaxResult stopTrain(@PathVariable("name") String name)
    {
        Client client = clientService.selectClientByName(name);
        Integer active = 0;
        client.setActive(active);
        clientService.updateClient(client);
        mcStopTrainCommandHandler.stopTrain(name);
        return success();
    }

    @PreAuthorize("@ss.hasPermi('business:client:query')")
    @GetMapping(value = "/active/{clientId}")
    public AjaxResult active(@PathVariable("clientId") Long clientId)
    {
        Client client = clientService.selectClientById(clientId);
        Integer active = client.getActive() == ACTIVATE.getValue() ? DEACTIVATE.getValue() : ACTIVATE.getValue();
        client.setActive(active);
        clientService.updateClient(client);
        if (active == DEACTIVATE.getValue()) {
            clientInfoManager.removeClient(client.getName());
        }
        return success(client.getActive());
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
