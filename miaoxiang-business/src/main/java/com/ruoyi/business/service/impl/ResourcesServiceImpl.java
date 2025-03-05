package com.ruoyi.business.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ruoyi.business.domain.request.ResourcesRequest;
import com.ruoyi.business.domain.response.ResourcesResponse;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ResourcesMapper;
import com.ruoyi.business.domain.Resources;
import com.ruoyi.business.service.IResourcesService;
import org.springframework.web.multipart.MultipartFile;

import static com.ruoyi.common.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;
import static com.ruoyi.common.utils.DateUtils.dateTimeNow;


/**
 * 资源Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-20
 */
@Slf4j
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

    @Override
    public List<ResourcesResponse> selectImagesList(ResourcesRequest resourcesRequest) {
        String directoryPath = frontendPublicDir + "/" + resourcesRequest.getProjectName() + '/' + resourcesRequest.getAssignmentName();
        Path dir = Paths.get(directoryPath);
        List<Path> bmpFiles = new ArrayList<>();
        List<String> jsonFileNames = new ArrayList<>();


        try (Stream<Path> paths = Files.list(dir)) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        String fileName = file.getFileName().toString();
                        if (resourcesRequest.getImgName() == null || fileName.contains(resourcesRequest.getImgName())) {
                            if (fileName.endsWith(".bmp")) {
                                bmpFiles.add(file);
                            } else if (fileName.endsWith(".json")) {
                                jsonFileNames.add(fileName.replace(".json", "")); // 添加去掉扩展名的 JSON 文件名
                            }
                        }
                    });
        } catch (IOException e) {
            return new ArrayList<>();
        }

        return getFileList(resourcesRequest, bmpFiles, jsonFileNames);
    }

    private List<ResourcesResponse> getFileList(ResourcesRequest resourcesRequest, List<Path> bmpFiles, List<String> jsonFileNames) {
        List<ResourcesResponse> result = new ArrayList<>();
        int count = 0;
        for (Path bmpFile : bmpFiles) {
            String fileNameWithoutExtension = bmpFile.getFileName().toString().replace(".bmp", "");
            if (jsonFileNames.contains(fileNameWithoutExtension)) {
                if (resourcesRequest.getState() == null || resourcesRequest.getState() == 1) {
                    fillFileResult(result, bmpFile, true);
                }
            } else {
                count ++;
                if (resourcesRequest.getState() == null || resourcesRequest.getState() == 0) {
                    fillFileResult(result, bmpFile, false);
                }
            }
        }
        int finalCount = count;
        result.forEach(e-> e.setUnMarkedCount(finalCount));
        return result;
    }

    private void fillFileResult(List<ResourcesResponse> result, Path bmpFile, boolean hasJson) {
        String createDate = DateUtils.getNowDate().toString();
        try {
            Date date = new Date(Files.readAttributes(bmpFile, BasicFileAttributes.class).creationTime().toMillis());

            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            createDate =  sdf.format(date);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        String fullPath = bmpFile.toFile().getPath();
        String normalizedFrontendDir = frontendPublicDir.replace("\\", "/");
        String normalizedFullPath = fullPath.replace("\\", "/");
        String relativePath = normalizedFullPath.substring(normalizedFrontendDir.length() + 1);
        ResourcesResponse response = new ResourcesResponse(relativePath, bmpFile.getFileName().toString(), createDate, hasJson);

        if (!hasJson) {
            result.add(0, response);
        } else {
            result.add(response);
        }
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
                + resources.getAssignmentName() + "/";
        Path targetDir = Paths.get(imgPath);
        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            files.stream()
                    .filter(file -> !file.isEmpty())
                    .forEach(file -> {
                        try {
                            String originalFileName = file.getOriginalFilename();
                            Path targetLocation = targetDir.resolve(originalFileName);
                            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new UtilException("文件保存失败");
                        }
                    });

            return (int) files.stream().filter(file -> !file.isEmpty()).count();
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
     * @param names 需要删除的资源路径
     * @return 结果
     */
    @Override
    public int deleteResourcesByIds(String[] names)
    {
        int deletedCount = 0;
        for (String name : names) {
            try {

                Path path = Paths.get(frontendPublicDir + '/' + name);
                if (Files.isDirectory(path)) {
                    deletedCount += deleteDirectory(path);
                } else {
                    if (Files.deleteIfExists(path)) {
                        deletedCount++;
                    } else {
                        log.error("BMP file not found or could not be deleted: {}", path);
                    }
                }

            } catch (IOException e) {
                log.error("Error deleting file: {}", e.getMessage());
            }
        }
        return deletedCount;
    }

    private int deleteDirectory(Path directory) throws IOException {
        int deletedCount = 0;
        try (Stream<Path> paths = Files.walk(directory)) {
            for (Path path : paths.collect(Collectors.toList())) {
                deletedCount += Files.deleteIfExists(path) ? 1 : 0;
            }
        }
        return deletedCount;
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted file: " + path);
            } else {
                System.out.println("Failed to delete file: " + path);
            }
        } else {
            System.out.println("File not found: " + path);
        }
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
