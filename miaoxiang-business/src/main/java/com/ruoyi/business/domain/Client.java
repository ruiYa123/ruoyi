package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户端对象 client
 *
 * @author ruoyi
 * @date 2025-02-11
 */
public class Client extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 客户端名称 */
    @Excel(name = "客户端名称")
    private String name;

    /** 客户端ip */
    @Excel(name = "客户端ip")
    private String ip;

    /** 客户端端口 */
    @Excel(name = "客户端端口")
    private Integer port;

    /** 状态 */
    @Excel(name = "状态")
    private Integer state;

    @Excel(name = "激活状态")
    private Integer active;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }
    public void setPort(Integer port)
    {
        this.port = port;
    }

    public Integer getPort()
    {
        return port;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState()
    {
        return state;
    }

    public void setActive(Integer active)
    {
        this.active = active;
    }

    public Integer getActive()
    {
        return active;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("ip", getIp())
                .append("port", getPort())
                .append("state", getState())
                .append("createTime", getCreateTime())
                .toString();
    }
}
