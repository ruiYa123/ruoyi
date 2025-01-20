package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ResourcesMapper;
import com.ruoyi.business.domain.Resources;
import com.ruoyi.business.service.IResourcesService;

/**
 * 资源Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-20
 */
@Service
public class ResourcesServiceImpl implements IResourcesService 
{
    @Autowired
    private ResourcesMapper resourcesMapper;

    /**
     * 查询资源
     * 
     * @param id 资源主键
     * @return 资源
     */
    @Override
    public Resources selectResourcesById(Long id)
    {
        return resourcesMapper.selectResourcesById(id);
    }

    /**
     * 查询资源列表
     * 
     * @param resources 资源
     * @return 资源
     */
    @Override
    public List<Resources> selectResourcesList(Resources resources)
    {
        return resourcesMapper.selectResourcesList(resources);
    }

    /**
     * 新增资源
     * 
     * @param resources 资源
     * @return 结果
     */
    @Override
    public int insertResources(Resources resources)
    {
        resources.setCreateTime(DateUtils.getNowDate());
        return resourcesMapper.insertResources(resources);
    }

    /**
     * 修改资源
     * 
     * @param resources 资源
     * @return 结果
     */
    @Override
    public int updateResources(Resources resources)
    {
        resources.setUpdateTime(DateUtils.getNowDate());
        return resourcesMapper.updateResources(resources);
    }

    /**
     * 批量删除资源
     * 
     * @param ids 需要删除的资源主键
     * @return 结果
     */
    @Override
    public int deleteResourcesByIds(Long[] ids)
    {
        return resourcesMapper.deleteResourcesByIds(ids);
    }

    /**
     * 删除资源信息
     * 
     * @param id 资源主键
     * @return 结果
     */
    @Override
    public int deleteResourcesById(Long id)
    {
        return resourcesMapper.deleteResourcesById(id);
    }
}
