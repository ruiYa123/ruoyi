package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.Resources;

/**
 * 资源Service接口
 * 
 * @author ruoyi
 * @date 2025-01-20
 */
public interface IResourcesService 
{
    /**
     * 查询资源
     * 
     * @param id 资源主键
     * @return 资源
     */
    public Resources selectResourcesById(Long id);

    /**
     * 查询资源列表
     * 
     * @param resources 资源
     * @return 资源集合
     */
    public List<Resources> selectResourcesList(Resources resources);

    /**
     * 新增资源
     * 
     * @param resources 资源
     * @return 结果
     */
    public int insertResources(Resources resources);

    /**
     * 修改资源
     * 
     * @param resources 资源
     * @return 结果
     */
    public int updateResources(Resources resources);

    /**
     * 批量删除资源
     * 
     * @param ids 需要删除的资源主键集合
     * @return 结果
     */
    public int deleteResourcesByIds(Long[] ids);

    /**
     * 删除资源信息
     * 
     * @param id 资源主键
     * @return 结果
     */
    public int deleteResourcesById(Long id);
}
