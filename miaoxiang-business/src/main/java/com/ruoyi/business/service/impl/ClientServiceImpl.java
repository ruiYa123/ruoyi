package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ClientMapper;
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.service.IClientService;

/**
 * 客户端Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-16
 */
@Service
public class ClientServiceImpl implements IClientService 
{
    @Autowired
    private ClientMapper clientMapper;

    /**
     * 查询客户端
     * 
     * @param id 客户端主键
     * @return 客户端
     */
    @Override
    public Client selectClientById(Long id)
    {
        return clientMapper.selectClientById(id);
    }

    /**
     * 查询客户端列表
     * 
     * @param client 客户端
     * @return 客户端
     */
    @Override
    public List<Client> selectClientList(Client client)
    {
        return clientMapper.selectClientList(client);
    }

    /**
     * 新增客户端
     * 
     * @param client 客户端
     * @return 结果
     */
    @Override
    public int insertClient(Client client)
    {
        client.setCreateTime(DateUtils.getNowDate());
        return clientMapper.insertClient(client);
    }

    /**
     * 修改客户端
     * 
     * @param client 客户端
     * @return 结果
     */
    @Override
    public int updateClient(Client client)
    {
        return clientMapper.updateClient(client);
    }

    /**
     * 批量删除客户端
     * 
     * @param ids 需要删除的客户端主键
     * @return 结果
     */
    @Override
    public int deleteClientByIds(Long[] ids)
    {
        return clientMapper.deleteClientByIds(ids);
    }

    /**
     * 删除客户端信息
     * 
     * @param id 客户端主键
     * @return 结果
     */
    @Override
    public int deleteClientById(Long id)
    {
        return clientMapper.deleteClientById(id);
    }
}
