package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Client;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
public interface ClientMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Client selectClientById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param client 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Client> selectClientList(Client client);

    /**
     * 新增【请填写功能名称】
     * 
     * @param client 【请填写功能名称】
     * @return 结果
     */
    public int insertClient(Client client);

    /**
     * 修改【请填写功能名称】
     * 
     * @param client 【请填写功能名称】
     * @return 结果
     */
    public int updateClient(Client client);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteClientById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientByIds(Long[] ids);
}
