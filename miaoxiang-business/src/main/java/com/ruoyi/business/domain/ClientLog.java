package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户端操作日志对象 client_log
 *
 * @author ruoyi
 * @date 2025-02-11
 */
public class ClientLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联的客户端ID */
    @Excel(name = "关联的客户端ID")
    private Long clientId;

    /** 客户端执行的命令字符串 */
    @Excel(name = "客户端执行的命令字符串")
    private String commandStr;

    /** 日志状态 */
    @Excel(name = "日志状态")
    private Long state;

    /** 日志详细内容 */
    @Excel(name = "日志详细内容")
    private String content;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public Long getClientId()
    {
        return clientId;
    }
    public void setCommandStr(String commandStr)
    {
        this.commandStr = commandStr;
    }

    public String getCommandStr()
    {
        return commandStr;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
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
            .append("clientId", getClientId())
            .append("commandStr", getCommandStr())
            .append("state", getState())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
