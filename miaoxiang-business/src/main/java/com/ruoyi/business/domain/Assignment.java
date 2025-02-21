package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 任务对象 assignment
 *
 * @author ruoyi
 * @date 2025-01-17
 */
public class Assignment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String assignmentName;

    /** 关联的项目ID */
    @Excel(name = "关联的项目ID")
    private Long projectId;

    /** 关联的模型ID */
    @Excel(name = "关联的模型ID")
    private Long modelId;

    private String clientName;

    /** 训练网络的预训练模式 */
    @Excel(name = "训练网络的预训练模式")
    private String pretrainMode;

    /** 训练次数 */
    @Excel(name = "训练次数")
    private Integer epoch;

    /** 每次训练的批大小 */
    @Excel(name = "每次训练的批大小")
    private Integer batchSize;

    /** 输入图像的大小 */
    @Excel(name = "输入图像的大小")
    private Integer imgSize;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private Integer state;

    private Long dept;

    private Date  jumpTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAssignmentName(String assignmentName)
    {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentName()
    {
        return assignmentName;
    }
    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }
    public void setModelId(Long modelId)
    {
        this.modelId = modelId;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public String getClientName()
    {
        return clientName;
    }
    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public void setPretrainMode(String pretrainMode)
    {
        this.pretrainMode = pretrainMode;
    }

    public String getPretrainMode()
    {
        return pretrainMode;
    }
    public void setEpoch(Integer epoch)
    {
        this.epoch = epoch;
    }

    public Integer getEpoch()
    {
        return epoch;
    }
    public void setBatchSize(Integer batchSize)
    {
        this.batchSize = batchSize;
    }

    public Integer getBatchSize()
    {
        return batchSize;
    }
    public void setImgSize(Integer imgSize)
    {
        this.imgSize = imgSize;
    }

    public Long getDept()
    {
        return dept;
    }

    public void setDept(Long dept)
    {
        this.dept = dept;
    }

    public Integer getImgSize()
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
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState()
    {
        return state;
    }

    public Date getJumpTime()
    {
        return jumpTime;
    }

    public void setJumpTime(Date jumpTime)
    {
        this.jumpTime = jumpTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("assignmentName", getAssignmentName())
                .append("projectId", getProjectId())
                .append("modelId", getModelId())
                .append("clientName", getClientName())
                .append("pretrainMode", getPretrainMode())
                .append("epoch", getEpoch())
                .append("batchSize", getBatchSize())
                .append("imgSize", getImgSize())
                .append("description", getDescription())
                .append("state", getState())
                .append("dept", getDept())
                .append("jumpTime", getJumpTime())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
