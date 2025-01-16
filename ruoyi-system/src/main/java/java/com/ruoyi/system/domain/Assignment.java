package java.com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务对象 assignment
 *
 * @author ruoyi
 * @date 2025-01-16
 */
public class Assignment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private String id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String assignmentName;

    /** 关联的项目ID */
    @Excel(name = "关联的项目ID")
    private String projectId;

    /** 关联的模型ID */
    @Excel(name = "关联的模型ID")
    private String modelId;

    /** 训练网络的预训练模式 */
    @Excel(name = "训练网络的预训练模式")
    private String pretrainMode;

    /** 训练次数 */
    @Excel(name = "训练次数")
    private String epoch;

    /** 每次训练的批大小 */
    @Excel(name = "每次训练的批大小")
    private String batchSize;

    /** 输入图像的大小 */
    @Excel(name = "输入图像的大小")
    private String imgSize;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
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
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectId()
    {
        return projectId;
    }
    public void setModelId(String modelId)
    {
        this.modelId = modelId;
    }

    public String getModelId()
    {
        return modelId;
    }
    public void setPretrainMode(String pretrainMode)
    {
        this.pretrainMode = pretrainMode;
    }

    public String getPretrainMode()
    {
        return pretrainMode;
    }
    public void setEpoch(String epoch)
    {
        this.epoch = epoch;
    }

    public String getEpoch()
    {
        return epoch;
    }
    public void setBatchSize(String batchSize)
    {
        this.batchSize = batchSize;
    }

    public String getBatchSize()
    {
        return batchSize;
    }
    public void setImgSize(String imgSize)
    {
        this.imgSize = imgSize;
    }

    public String getImgSize()
    {
        return imgSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("assignmentName", getAssignmentName())
            .append("projectId", getProjectId())
            .append("modelId", getModelId())
            .append("pretrainMode", getPretrainMode())
            .append("epoch", getEpoch())
            .append("batchSize", getBatchSize())
            .append("imgSize", getImgSize())
            .toString();
    }
}
