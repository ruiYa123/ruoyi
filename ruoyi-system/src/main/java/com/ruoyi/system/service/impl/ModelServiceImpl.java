package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ModelMapper;
import com.ruoyi.system.domain.Model;
import com.ruoyi.system.service.IModelService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-15
 */
@Service
public class ModelServiceImpl implements IModelService 
{
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Model selectModelById(Long id)
    {
        return modelMapper.selectModelById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param model 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Model> selectModelList(Model model)
    {
        return modelMapper.selectModelList(model);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param model 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertModel(Model model)
    {
        model.setCreateTime(DateUtils.getNowDate());
        return modelMapper.insertModel(model);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param model 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateModel(Model model)
    {
        model.setUpdateTime(DateUtils.getNowDate());
        return modelMapper.updateModel(model);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelByIds(Long[] ids)
    {
        return modelMapper.deleteModelByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelById(Long id)
    {
        return modelMapper.deleteModelById(id);
    }
}
