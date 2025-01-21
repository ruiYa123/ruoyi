package com.ruoyi.business.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ruoyi.business.domain.request.ResourcesRequest;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ResourcesMapper;
import com.ruoyi.business.domain.Resources;
import com.ruoyi.business.service.IResourcesService;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${frontend.public.directory}")
    private String frontendPublicDir;

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
    public int insertResources(ResourcesRequest resources, List<MultipartFile>  files) {
        String imgPath = frontendPublicDir + "/"
                + resources.getProjectName() + "/"
                + resources.getAssignmentName() + "/"
                + (resources.getState().equals(0L) ? "未打标" : "已打标") + "/" ;
        resources.setCreateTime(DateUtils.getNowDate());
        Path targetDir = Paths.get(imgPath);
        List<Resources> resourcesList = new ArrayList<>();
        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }
            int fileIndex = 0;
            for (MultipartFile file : files) {
                Resources resourcesAdded = new Resources();
                BeanUtils.copyProperties(resources, resourcesAdded);
                if (!file.isEmpty()) {
                    String originalFileName = file.getOriginalFilename();
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String uniqueFileName = UUID.randomUUID() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileExtension;
                    Path targetLocation = targetDir.resolve(uniqueFileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                    if (fileIndex == 0) {
                        resourcesAdded.setImgName(resourcesAdded.getImgName() + fileExtension);
                    } else {
                        resourcesAdded.setImgName(resources.getImgName() + "(" + fileIndex + ")" + fileExtension);
                    }
                    resourcesAdded.setImgPath(imgPath + uniqueFileName);
                    resourcesAdded.setImgSize(file.getSize());
                    resourcesList.add(resourcesAdded);
                    fileIndex++;
                }
            }
            return resourcesMapper.batchInsertResources(resourcesList);
        } catch (IOException e) {
            throw new UtilException("文件保存失败");
        }
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
