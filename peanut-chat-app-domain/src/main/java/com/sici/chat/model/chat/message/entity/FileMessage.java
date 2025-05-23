package com.sici.chat.model.chat.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * @TableName file_message
 */
@TableName(value ="file_message")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMessage {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 文件url地址
     */
    private String url;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 附加信息
     */
    private String extra;

    /**
     * 文件类型(1:音频，2:视频，3:图片，4:普通文件)
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 消息id
     */
    public Long getMsgId() {
        return msgId;
    }

    /**
     * 消息id
     */
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    /**
     * 文件url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 文件url地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 附加信息
     */
    public String getExtra() {
        return extra;
    }

    /**
     * 附加信息
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }

    /**
     * 文件类型(1:音频，2:视频，3:图片，4:普通文件)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 文件类型(1:音频，2:视频，3:图片，4:普通文件)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FileMessage other = (FileMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getSize() == null ? other.getSize() == null : this.getSize().equals(other.getSize()))
            && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getSize() == null) ? 0 : getSize().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msgId=").append(msgId);
        sb.append(", url=").append(url);
        sb.append(", size=").append(size);
        sb.append(", extra=").append(extra);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}