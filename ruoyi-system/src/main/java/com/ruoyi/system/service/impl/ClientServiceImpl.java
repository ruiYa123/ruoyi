package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ClientMapper;
import com.ruoyi.system.domain.Client;
import com.ruoyi.system.service.IClientService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
@Service
public class ClientServiceImpl implements IClientService 
{
    @Autowired
    private ClientMapper clientMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Client selectClientById(Long id)
    {
        return clientMapper.selectClientById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param client 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Client> selectClientList(Client client)
    {
        return clientMapper.selectClientList(client);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param client 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertClient(Client client)
    {
        client.setCreateTime(DateUtils.getNowDate());
        return clientMapper.insertClient(client);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param client 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateClient(Client client)
    {
        return clientMapper.updateClient(client);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteClientByIds(Long[] ids)
    {
        return clientMapper.deleteClientByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteClientById(Long id)
    {
        return clientMapper.deleteClientById(id);
    }
}
