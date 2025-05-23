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
 * @TableName message
 */
@TableName(value ="message")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * 消息id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送者id
     */
    private Long fromUid;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息内容类型 （0：文本消息，1:音频消息,2:视频消息，3:图片，4:普通文件)
     */
    private Integer type;

    /**
     * 回复的消息id
     */
    private Long replyMsgId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0未撤回，1已撤回
     */
    private Integer status;

    /**
     * 扩展信息
     */
    private String extra;

    /**
     * 消息id
     */
    public Long getId() {
        return id;
    }

    /**
     * 消息id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 发送者id
     */
    public Long getFromUid() {
        return fromUid;
    }

    /**
     * 发送者id
     */
    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    /**
     * 房间id
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * 房间id
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 消息内容类型 （0：文本消息，1:音频消息,2:视频消息，3:图片，4:普通文件)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 消息内容类型 （0：文本消息，1:音频消息,2:视频消息，3:图片，4:普通文件)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 回复的消息id
     */
    public Long getReplyMsgId() {
        return replyMsgId;
    }

    /**
     * 回复的消息id
     */
    public void setReplyMsgId(Long replyMsgId) {
        this.replyMsgId = replyMsgId;
    }

    /**
     * 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

    /**
     * 0未撤回，1已撤回
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0未撤回，1已撤回
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 扩展信息
     */
    public String getExtra() {
        return extra;
    }

    /**
     * 扩展信息
     */
    public void setExtra(String extra) {
        this.extra = extra;
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
        Message other = (Message) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFromUid() == null ? other.getFromUid() == null : this.getFromUid().equals(other.getFromUid()))
            && (this.getRoomId() == null ? other.getRoomId() == null : this.getRoomId().equals(other.getRoomId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getReplyMsgId() == null ? other.getReplyMsgId() == null : this.getReplyMsgId().equals(other.getReplyMsgId()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFromUid() == null) ? 0 : getFromUid().hashCode());
        result = prime * result + ((getRoomId() == null) ? 0 : getRoomId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getReplyMsgId() == null) ? 0 : getReplyMsgId().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromUid=").append(fromUid);
        sb.append(", roomId=").append(roomId);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", replyMsgId=").append(replyMsgId);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", extra=").append(extra);
        sb.append("]");
        return sb.toString();
    }
}