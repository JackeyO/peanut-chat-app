package com.sici.chat.model.chat.room.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName room_action
 */
@TableName(value ="room_action")
public class RoomAction implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 最后一次活跃时间
     */
    private Date lastActiveTime;

    /**
     * 0不是热房间，1：是热房间
     */
    private Integer hotFlag;

    /**
     * 该房间最后一条消息id
     */
    private Integer lastMsgId;

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
     * 房间id
     */
    public Integer getRoomId() {
        return roomId;
    }

    /**
     * 房间id
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    /**
     * 最后一次活跃时间
     */
    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    /**
     * 最后一次活跃时间
     */
    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    /**
     * 0不是热房间，1：是热房间
     */
    public Integer getHotFlag() {
        return hotFlag;
    }

    /**
     * 0不是热房间，1：是热房间
     */
    public void setHotFlag(Integer hotFlag) {
        this.hotFlag = hotFlag;
    }

    /**
     * 该房间最后一条消息id
     */
    public Integer getLastMsgId() {
        return lastMsgId;
    }

    /**
     * 该房间最后一条消息id
     */
    public void setLastMsgId(Integer lastMsgId) {
        this.lastMsgId = lastMsgId;
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
        RoomAction other = (RoomAction) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoomId() == null ? other.getRoomId() == null : this.getRoomId().equals(other.getRoomId()))
            && (this.getLastActiveTime() == null ? other.getLastActiveTime() == null : this.getLastActiveTime().equals(other.getLastActiveTime()))
            && (this.getHotFlag() == null ? other.getHotFlag() == null : this.getHotFlag().equals(other.getHotFlag()))
            && (this.getLastMsgId() == null ? other.getLastMsgId() == null : this.getLastMsgId().equals(other.getLastMsgId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoomId() == null) ? 0 : getRoomId().hashCode());
        result = prime * result + ((getLastActiveTime() == null) ? 0 : getLastActiveTime().hashCode());
        result = prime * result + ((getHotFlag() == null) ? 0 : getHotFlag().hashCode());
        result = prime * result + ((getLastMsgId() == null) ? 0 : getLastMsgId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roomId=").append(roomId);
        sb.append(", lastActiveTime=").append(lastActiveTime);
        sb.append(", hotFlag=").append(hotFlag);
        sb.append(", lastMsgId=").append(lastMsgId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}