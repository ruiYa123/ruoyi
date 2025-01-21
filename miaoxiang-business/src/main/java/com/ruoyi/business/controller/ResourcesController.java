package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.business.domain.request.ResourcesRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.business.domain.Resources;
import com.ruoyi.business.service.IResourcesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 资源Controller
 *
 * @author ruoyi
 * @date 2025-01-20
 */
@RestController
@RequestMapping("/business/resources")
public class ResourcesController extends BaseController
{
    @Autowired
    private IResourcesService resourcesService;

    /**
     * 查询资源列表
     */
    @PreAuthorize("@ss.hasPermi('business:resources:list')")
    @GetMapping("/list")
    public TableDataInfo list(Resources resources)
    {
        startPage();
        List<Resources> list = resourcesService.selectResourcesList(resources);
        return getDataTable(list);
    }

    /**
     * 导出资源列表
     */
    @PreAuthorize("@ss.hasPermi('business:resources:export')")
    @Log(title = "资源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Resources resources)
    {
        List<Resources> list = resourcesService.selectResourcesList(resources);
        ExcelUtil<Resources> util = new ExcelUtil<Resources>(Resources.class);
        util.exportExcel(response, list, "资源数据");
    }

    /**
     * 获取资源详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:resources:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(resourcesService.selectResourcesById(id));
    }

    /**
     * 新增资源
     */
//    @PreAuthorize("@ss.hasPermi('business:resources:add')")
//    @Log(title = "资源", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestParam("imgName") String imgName,
//                          @RequestParam("assignmentId") Long assignmentId,
//                          @RequestParam("state") Long state,
//                          @RequestParam("description") String description,
//                          @RequestParam("assignmentName") String assignmentName,
//                          @RequestParam("projectName") String projectName,
//                          @RequestPart("files[]") List<MultipartFile> files)
//    {
//        Resources resources = new Resources();
//        resources.setAssignmentId(assignmentId);
//        resources.setImgName(imgName);
//        resources.setState(state);
//        resources.setDescription(description);
//        return toAjax(resourcesService.insertResources(resources,assignmentName, projectName, files));
//    }

    @PreAuthorize("@ss.hasPermi('business:resources:add')")
    @Log(title = "资源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ModelAttribute ResourcesRequest resourcesRequest,
                          @RequestPart("files[]") List<MultipartFile> files)
    {
        return toAjax(resourcesService.insertResources(resourcesRequest, files));
    }

    /**
     * 修改资源
     */
    @PreAuthorize("@ss.hasPermi('business:resources:edit')")
    @Log(title = "资源", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Resources resources)
    {
        return toAjax(resourcesService.updateResources(resources));
    }

    /**
     * 删除资源
     */
    @PreAuthorize("@ss.hasPermi('business:resources:remove')")
    @Log(title = "资源", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resourcesService.deleteResourcesByIds(ids));
    }
}
