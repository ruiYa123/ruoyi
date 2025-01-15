package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Model;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-01-15
 */
public interface IModelService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Model selectModelById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param model 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Model> selectModelList(Model model);

    /**
     * 新增【请填写功能名称】
     * 
     * @param model 【请填写功能名称】
     * @return 结果
     */
    public int insertModel(Model model);

    /**
     * 修改【请填写功能名称】
     * 
     * @param model 【请填写功能名称】
     * @return 结果
     */
    public int updateModel(Model model);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteModelByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteModelById(Long id);
}
