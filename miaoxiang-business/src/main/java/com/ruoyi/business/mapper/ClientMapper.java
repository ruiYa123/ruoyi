package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.Client;

/**
 * 客户端Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-16
 */
public interface ClientMapper
{
    /**
     * 查询客户端
     *
     * @param id 客户端主键
     * @return 客户端
     */
    public Client selectClientById(Long id);

    public Client selectClient(Client client);

    /**
     * 查询客户端列表
     *
     * @param client 客户端
     * @return 客户端集合
     */
    public List<Client> selectClientList(Client client);

    /**
     * 新增客户端
     *
     * @param client 客户端
     * @return 结果
     */
    public int insertClient(Client client);

    /**
     * 修改客户端
     *
     * @param client 客户端
     * @return 结果
     */
    public int updateClient(Client client);

    /**
     * 删除客户端
     *
     * @param id 客户端主键
     * @return 结果
     */
    public int deleteClientById(Long id);

    /**
     * 批量删除客户端
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientByIds(Long[] ids);

    /**
     * 根据name查询客户端
     *
     * @param name 客户端名称
     * @return 客户端
     */
    public Client selectClientByName(String name);

    /**
     * 根据name更新客户端
     *
     * @param client 客户端
     * @return 结果
     */
    public int updateClientByName(Client client);

    /**
     * 根据name删除客户端
     *
     * @param name 客户端名称
     * @return 结果
     */
    public int deleteClientByName(String name);
}
