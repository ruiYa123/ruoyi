package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 训练日志对象 train_log
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
public class TrainLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联的任务ID */
    @Excel(name = "关联的任务ID")
    private Long assignmentId;

    /** 关联的训练任务ID */
    @Excel(name = "关联的训练任务ID")
    private Long assignmentTrainId;

    /** 日志内容 */
    @Excel(name = "日志内容")
    private String content;

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
    public void setAssignmentTrainId(Long assignmentTrainId) 
    {
        this.assignmentTrainId = assignmentTrainId;
    }

    public Long getAssignmentTrainId() 
    {
        return assignmentTrainId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("assignmentId", getAssignmentId())
            .append("assignmentTrainId", getAssignmentTrainId())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
