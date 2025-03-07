package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Slf4j
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

    @Override
    public Client selectClient(Client client) {
        return clientMapper.selectClient(client);
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

    @Override
    public Long addClient(Client client)
    {
        Client clientSearchVO = new Client();
        clientSearchVO.setName(client.getName());
        Client clientResult = clientMapper.selectClient(clientSearchVO);
        if (clientResult == null) {
            client.setCreateTime(DateUtils.getNowDate());
            clientMapper.insertClient(client);
        } else {
            client.setId(clientResult.getId());
            if(client.getPort() == null) {
                client.setPort(clientResult.getPort());
            }
            if(client.getIp() == null) {
                client.setIp(clientResult.getIp());
            }
            if(client.getState() == null) {
                client.setState(clientResult.getState());
            }
            client.setUpdateTime(DateUtils.getNowDate());
            clientMapper.updateClient(client);
        }
        log.info(String.valueOf(client.getId()));
        return client.getId();

    }

    @Override
    public void offLineClient(Client client) {
        Client clientSearchVO = new Client();
        if (client.getName() != null) {
            clientSearchVO.setName(client.getName());
        } else if (client.getIp() != null && client.getPort() != null) {
            clientSearchVO.setIp(client.getIp());
            clientSearchVO.setPort(client.getPort());
        }
        Client clientResult = clientMapper.selectClient(clientSearchVO);
        if (clientResult != null && clientResult.getState() != 0) {
            log.info("与{}断开连接", clientResult.getName());
            clientResult.setState(2);
            clientMapper.updateClient(clientResult);
        }
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

    @Override
    public Client selectClientByName(String name) {
        return clientMapper.selectClientByName(name);
    }

    @Override
    public int updateClientByName(Client client) {
        return clientMapper.updateClientByName(client);
    }

    @Override
    public int deleteClientByName(String name) {
        return clientMapper.deleteClientByName(name);
    }
}
