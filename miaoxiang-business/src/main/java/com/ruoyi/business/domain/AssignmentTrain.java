package com.ruoyi.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务训练对象 assignment_train
 *
 * @author ruoyi
 * @date 2025-02-08
 */
public class AssignmentTrain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long assignmentId;

    private String clientName;

    /** 状态 */
    @Excel(name = "状态")
    private Integer state;

    /** 进度 */
    @Excel(name = "进度")
    private BigDecimal progress = BigDecimal.ZERO;

    /** 备注 */
    @Excel(name = "备注")
    private String description;


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAssignmentId(Long assignmentId)
    {
        this.assignmentId = assignmentId;
    }

    public Long getAssignmentId()
    {
        return assignmentId;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }
    public String getClientName()
    {
        return clientName;
    }
    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }


    public Integer getState()
    {
        return state;
    }
    public void setProgress(BigDecimal progress)
    {
        this.progress = progress;
    }

    public BigDecimal getProgress()
    {
        return progress;
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
            .append("assignmentId", getAssignmentId())
            .append("clientName", getClientName())
            .append("state", getState())
            .append("progress", getProgress())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
