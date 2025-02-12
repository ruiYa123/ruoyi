package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ClientLogMapper;
import com.ruoyi.business.domain.ClientLog;
import com.ruoyi.business.service.IClientLogService;

/**
 * 客户端操作日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-11
 */
@Service
public class ClientLogServiceImpl implements IClientLogService 
{
    @Autowired
    private ClientLogMapper clientLogMapper;

    /**
     * 查询客户端操作日志
     * 
     * @param id 客户端操作日志主键
     * @return 客户端操作日志
     */
    @Override
    public ClientLog selectClientLogById(Long id)
    {
        return clientLogMapper.selectClientLogById(id);
    }

    /**
     * 查询客户端操作日志列表
     * 
     * @param clientLog 客户端操作日志
     * @return 客户端操作日志
     */
    @Override
    public List<ClientLog> selectClientLogList(ClientLog clientLog)
    {
        return clientLogMapper.selectClientLogList(clientLog);
    }

    /**
     * 新增客户端操作日志
     * 
     * @param clientLog 客户端操作日志
     * @return 结果
     */
    @Override
    public int insertClientLog(ClientLog clientLog)
    {
        clientLog.setCreateTime(DateUtils.getNowDate());
        return clientLogMapper.insertClientLog(clientLog);
    }

    /**
     * 修改客户端操作日志
     * 
     * @param clientLog 客户端操作日志
     * @return 结果
     */
    @Override
    public int updateClientLog(ClientLog clientLog)
    {
        return clientLogMapper.updateClientLog(clientLog);
    }

    /**
     * 批量删除客户端操作日志
     * 
     * @param ids 需要删除的客户端操作日志主键
     * @return 结果
     */
    @Override
    public int deleteClientLogByIds(Long[] ids)
    {
        return clientLogMapper.deleteClientLogByIds(ids);
    }

    /**
     * 删除客户端操作日志信息
     * 
     * @param id 客户端操作日志主键
     * @return 结果
     */
    @Override
    public int deleteClientLogById(Long id)
    {
        return clientLogMapper.deleteClientLogById(id);
    }
}
