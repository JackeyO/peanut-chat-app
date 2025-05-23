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
 * @TableName message_mark
 */
@TableName(value ="message_mark")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageMark {
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
     * 执行操作的用户id
     */
    private Long userId;

    /**
     * 是否喜欢
     */
    private Integer likeFlag;

    /**
     * 是否不喜欢
     */
    private Integer dislikeFlag;

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
     * 执行操作的用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 执行操作的用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 是否喜欢
     */
    public Integer getLikeFlag() {
        return likeFlag;
    }

    /**
     * 是否喜欢
     */
    public void setLikeFlag(Integer likeFlag) {
        this.likeFlag = likeFlag;
    }

    /**
     * 是否不喜欢
     */
    public Integer getDislikeFlag() {
        return dislikeFlag;
    }

    /**
     * 是否不喜欢
     */
    public void setDislikeFlag(Integer dislikeFlag) {
        this.dislikeFlag = dislikeFlag;
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
        MessageMark other = (MessageMark) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getLikeFlag() == null ? other.getLikeFlag() == null : this.getLikeFlag().equals(other.getLikeFlag()))
            && (this.getDislikeFlag() == null ? other.getDislikeFlag() == null : this.getDislikeFlag().equals(other.getDislikeFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getLikeFlag() == null) ? 0 : getLikeFlag().hashCode());
        result = prime * result + ((getDislikeFlag() == null) ? 0 : getDislikeFlag().hashCode());
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
        sb.append(", userId=").append(userId);
        sb.append(", likeFlag=").append(likeFlag);
        sb.append(", dislikeFlag=").append(dislikeFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}