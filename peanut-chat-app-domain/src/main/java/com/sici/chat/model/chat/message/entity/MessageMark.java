package com.sici.chat.model.chat.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName message_mark
 */
@TableName(value ="message_mark")
public class MessageMark implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 消息id
     */
    private Integer msgId;

    /**
     * 执行操作的用户id
     */
    private Integer userId;

    /**
     * 是否喜欢
     */
    private Integer like;

    /**
     * 是否不喜欢
     */
    private Integer dislike;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 消息id
     */
    public Integer getMsgId() {
        return msgId;
    }

    /**
     * 消息id
     */
    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    /**
     * 执行操作的用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 执行操作的用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 是否喜欢
     */
    public Integer getLike() {
        return like;
    }

    /**
     * 是否喜欢
     */
    public void setLike(Integer like) {
        this.like = like;
    }

    /**
     * 是否不喜欢
     */
    public Integer getDislike() {
        return dislike;
    }

    /**
     * 是否不喜欢
     */
    public void setDislike(Integer dislike) {
        this.dislike = dislike;
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
            && (this.getLike() == null ? other.getLike() == null : this.getLike().equals(other.getLike()))
            && (this.getDislike() == null ? other.getDislike() == null : this.getDislike().equals(other.getDislike()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getLike() == null) ? 0 : getLike().hashCode());
        result = prime * result + ((getDislike() == null) ? 0 : getDislike().hashCode());
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
        sb.append(", like=").append(like);
        sb.append(", dislike=").append(dislike);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}