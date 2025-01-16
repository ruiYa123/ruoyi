package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Model;
import com.ruoyi.system.service.IModelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模型Controller
 *
 * @author ruoyi
 * @date 2025-01-16
 */
@RestController
@RequestMapping("/system/model")
public class ModelController extends BaseController
{
    @Autowired
    private IModelService modelService;

    /**
     * 查询模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(Model model)
    {
        startPage();
        List<Model> list = modelService.selectModelList(model);
        return getDataTable(list);
    }

    /**
     * 导出模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:model:export')")
    @Log(title = "模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Model model)
    {
        List<Model> list = modelService.selectModelList(model);
        ExcelUtil<Model> util = new ExcelUtil<Model>(Model.class);
        util.exportExcel(response, list, "模型数据");
    }

    /**
     * 获取模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(modelService.selectModelById(id));
    }

    /**
     * 新增模型
     */
    @PreAuthorize("@ss.hasPermi('system:model:add')")
    @Log(title = "模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Model model)
    {
        return toAjax(modelService.insertModel(model));
    }

    /**
     * 修改模型
     */
    @PreAuthorize("@ss.hasPermi('system:model:edit')")
    @Log(title = "模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Model model)
    {
        return toAjax(modelService.updateModel(model));
    }

    /**
     * 删除模型
     */
    @PreAuthorize("@ss.hasPermi('system:model:remove')")
    @Log(title = "模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(modelService.deleteModelByIds(ids));
    }
}
