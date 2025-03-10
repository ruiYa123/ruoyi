package com.ruoyi.business.controller;

import java.nio.file.Path;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.ruoyi.business.domain.request.DeleteResourcesRequest;
import com.ruoyi.business.domain.request.FileListRequest;
import com.ruoyi.business.domain.request.ResourcesRequest;
import com.ruoyi.business.domain.response.ResourcesResponse;
import com.ruoyi.business.service.impl.ResourcesServiceImpl;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ServletUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

import static com.ruoyi.common.core.page.TableSupport.PAGE_NUM;
import static com.ruoyi.common.core.page.TableSupport.PAGE_SIZE;

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

    @PreAuthorize("@ss.hasPermi('business:resources:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(ResourcesRequest resourcesRequest)
    {
        List<ResourcesResponse> list = resourcesService.selectImagesList(resourcesRequest);
        return success(list);
    }

    @GetMapping(value = "/getImage/{projectName}/{assignmentName}/{resourcesName}") // 允许路径中包含多个斜杠
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String projectName, @PathVariable String assignmentName, @PathVariable String resourcesName) {
        try {
            FileSystemResource image = resourcesService.getImage(projectName + "/" + assignmentName + "/" + resourcesName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/bmp");
            headers.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<>(image, headers, HttpStatus.SUCCESS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @PreAuthorize("@ss.hasPermi('business:resources:list')")
    @GetMapping("/imagelist")
    public TableDataInfo imagelist(ResourcesRequest resourcesRequest)
    {

        List<ResourcesResponse> list = resourcesService.selectImagesList(resourcesRequest);
        Integer pageNum = Convert.toInt(ServletUtils.getParameter(PAGE_NUM), 1);
        Integer pageSize = Convert.toInt(ServletUtils.getParameter(PAGE_SIZE), 10);
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, list.size());
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list.subList(fromIndex, toIndex));
        rspData.setTotal(list.size());
        return rspData;
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
    @PreAuthorize("@ss.hasPermi('business:resources:add')")
    @Log(title = "资源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ModelAttribute ResourcesRequest resourcesRequest,
                          @RequestPart("files[]") List<MultipartFile> files)
    {
        resourcesRequest.setCreateBy(getUsername());
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
        resources.setUpdateBy(getUsername());
        return toAjax(resourcesService.updateResources(resources));
    }

    /**
     * 删除资源
     */
    @PreAuthorize("@ss.hasPermi('business:resources:remove')")
    @Log(title = "资源", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    public AjaxResult remove(@RequestBody DeleteResourcesRequest deleteResourcesRequest)
    {
        return success(resourcesService.deleteResourcesByIds(deleteResourcesRequest.getPath().toArray(new String[0])));
    }
}
