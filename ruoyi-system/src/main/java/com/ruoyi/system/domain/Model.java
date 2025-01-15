package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 model
 * 
 * @author ruoyi
 * @date 2025-01-15
 */
public class Model extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模型ID */
    private Long id;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String modelName;

    /** 训练次数 */
    @Excel(name = "训练次数")
    private Long epoch;

    /** 批大小 */
    @Excel(name = "批大小")
    private Long batchSize;

    /** 图像大小 */
    @Excel(name = "图像大小")
    private Long imgSize;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setModelName(String modelName) 
    {
        this.modelName = modelName;
    }

    public String getModelName() 
    {
        return modelName;
    }
    public void setEpoch(Long epoch) 
    {
        this.epoch = epoch;
    }

    public Long getEpoch() 
    {
        return epoch;
    }
    public void setBatchSize(Long batchSize) 
    {
        this.batchSize = batchSize;
    }

    public Long getBatchSize() 
    {
        return batchSize;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("modelName", getModelName())
            .append("epoch", getEpoch())
            .append("batchSize", getBatchSize())
            .append("imgSize", getImgSize())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
