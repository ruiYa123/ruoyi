package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.Model;

/**
 * 模型Service接口
 * 
 * @author ruoyi
 * @date 2025-01-16
 */
public interface IModelService 
{
    /**
     * 查询模型
     * 
     * @param id 模型主键
     * @return 模型
     */
    public Model selectModelById(Long id);

    /**
     * 查询模型列表
     * 
     * @param model 模型
     * @return 模型集合
     */
    public List<Model> selectModelList(Model model);

    /**
     * 新增模型
     * 
     * @param model 模型
     * @return 结果
     */
    public int insertModel(Model model);

    /**
     * 修改模型
     * 
     * @param model 模型
     * @return 结果
     */
    public int updateModel(Model model);

    /**
     * 批量删除模型
     * 
     * @param ids 需要删除的模型主键集合
     * @return 结果
     */
    public int deleteModelByIds(Long[] ids);

    /**
     * 删除模型信息
     * 
     * @param id 模型主键
     * @return 结果
     */
    public int deleteModelById(Long id);
}
