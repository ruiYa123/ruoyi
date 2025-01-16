package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ModelMapper;
import com.ruoyi.system.domain.Model;
import com.ruoyi.system.service.IModelService;

/**
 * 模型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-16
 */
@Service
public class ModelServiceImpl implements IModelService
{
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 查询模型
     *
     * @param id 模型主键
     * @return 模型
     */
    @Override
    public Model selectModelById(Long id)
    {
        return modelMapper.selectModelById(id);
    }

    /**
     * 查询模型列表
     *
     * @param model 模型
     * @return 模型
     */
    @Override
    public List<Model> selectModelList(Model model)
    {
        return modelMapper.selectModelList(model);
    }

    /**
     * 新增模型
     *
     * @param model 模型
     * @return 结果
     */
    @Override
    public int insertModel(Model model)
    {
        model.setCreateTime(DateUtils.getNowDate());
        return modelMapper.insertModel(model);
    }

    /**
     * 修改模型
     *
     * @param model 模型
     * @return 结果
     */
    @Override
    public int updateModel(Model model)
    {
        model.setUpdateTime(DateUtils.getNowDate());
        return modelMapper.updateModel(model);
    }

    /**
     * 批量删除模型
     *
     * @param ids 需要删除的模型主键
     * @return 结果
     */
    @Override
    public int deleteModelByIds(Long[] ids)
    {
        return modelMapper.deleteModelByIds(ids);
    }

    /**
     * 删除模型信息
     *
     * @param id 模型主键
     * @return 结果
     */
    @Override
    public int deleteModelById(Long id)
    {
        return modelMapper.deleteModelById(id);
    }
}
