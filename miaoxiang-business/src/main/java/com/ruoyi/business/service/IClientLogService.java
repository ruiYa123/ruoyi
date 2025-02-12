package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.ClientLog;

/**
 * 客户端操作日志Service接口
 * 
 * @author ruoyi
 * @date 2025-02-11
 */
public interface IClientLogService 
{
    /**
     * 查询客户端操作日志
     * 
     * @param id 客户端操作日志主键
     * @return 客户端操作日志
     */
    public ClientLog selectClientLogById(Long id);

    /**
     * 查询客户端操作日志列表
     * 
     * @param clientLog 客户端操作日志
     * @return 客户端操作日志集合
     */
    public List<ClientLog> selectClientLogList(ClientLog clientLog);

    /**
     * 新增客户端操作日志
     * 
     * @param clientLog 客户端操作日志
     * @return 结果
     */
    public int insertClientLog(ClientLog clientLog);

    /**
     * 修改客户端操作日志
     * 
     * @param clientLog 客户端操作日志
     * @return 结果
     */
    public int updateClientLog(ClientLog clientLog);

    /**
     * 批量删除客户端操作日志
     * 
     * @param ids 需要删除的客户端操作日志主键集合
     * @return 结果
     */
    public int deleteClientLogByIds(Long[] ids);

    /**
     * 删除客户端操作日志信息
     * 
     * @param id 客户端操作日志主键
     * @return 结果
     */
    public int deleteClientLogById(Long id);
}
