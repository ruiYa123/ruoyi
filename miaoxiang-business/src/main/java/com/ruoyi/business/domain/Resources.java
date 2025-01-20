package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 资源对象 resources
 * 
 * @author ruoyi
 * @date 2025-01-20
 */
public class Resources extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 图片名称 */
    @Excel(name = "图片名称")
    private String imgName;

    /** 关联的任务ID */
    @Excel(name = "关联的任务ID")
    private Long assignmentId;

    /** 图片存储路径 */
    @Excel(name = "图片存储路径")
    private String imgPath;

    /** 图片大小（字节） */
    @Excel(name = "图片大小", readConverterExp = "字=节")
    private Long imgSize;

    /** 图片描述 */
    @Excel(name = "图片描述")
    private String description;

    /** 图片状态（0-未打标，1-已打标） */
    @Excel(name = "图片状态", readConverterExp = "0=-未打标，1-已打标")
    private Long state;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setImgName(String imgName) 
    {
        this.imgName = imgName;
    }

    public String getImgName() 
    {
        return imgName;
    }
    public void setAssignmentId(Long assignmentId) 
    {
        this.assignmentId = assignmentId;
    }

    public Long getAssignmentId() 
    {
        return assignmentId;
    }
    public void setImgPath(String imgPath) 
    {
        this.imgPath = imgPath;
    }

    public String getImgPath() 
    {
        return imgPath;
    }
    public void setImgSize(Long imgSize) 
    {
        this.imgSize = imgSize;
    }

    public Long getImgSize() 
    {
        return imgSize;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("imgName", getImgName())
            .append("assignmentId", getAssignmentId())
            .append("imgPath", getImgPath())
            .append("imgSize", getImgSize())
            .append("description", getDescription())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
