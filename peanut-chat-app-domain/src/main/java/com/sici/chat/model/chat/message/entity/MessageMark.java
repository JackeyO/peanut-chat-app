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
    @TableId(type = IdType.AUTO)
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
    private Integer likeFlag;

    /**
     * 是否不喜欢
     */
    private Integer dislikeFlag;

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
    public Integer getLikeFlag() {
        return likeFlag;
    }

    /**
     * 是否喜欢
     */
    public void setLikeFlag(Integer like) {
        this.likeFlag = like;
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
            && (this.getDislikeFlag() == null ? other.getDislikeFlag() == null : this.getDislikeFlag().equals(other.getDislikeFlag()));
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
        sb.append(", like=").append(likeFlag);
        sb.append(", dislikeFlag=").append(dislikeFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}