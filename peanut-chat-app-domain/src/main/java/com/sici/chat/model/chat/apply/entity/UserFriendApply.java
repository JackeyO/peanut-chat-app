package com.sici.chat.model.chat.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * @TableName user_friend_apply
 */
@TableName(value ="user_friend_apply")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendApply {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 申请人id
     */
    private Long uid;

    /**
     * 申请的目标对象id
     */
    private Long targetId;

    /**
     * 申请发起时间
     */
    private Date applyTime;

    /**
     * 申请消息内容
     */
    private String applyMsg;

    /**
     * 0：请求被拒绝，1：请求被接受 2:请求还未被处理
     */
    private Integer acceptStatus;

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
     * 申请人id
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 申请人id
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 申请的目标对象id
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * 申请的目标对象id
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * 申请发起时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 申请发起时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 申请消息内容
     */
    public String getApplyMsg() {
        return applyMsg;
    }

    /**
     * 申请消息内容
     */
    public void setApplyMsg(String applyMsg) {
        this.applyMsg = applyMsg;
    }

    /**
     * 0：请求被拒绝，1：请求被接受 2:请求还未被处理
     */
    public Integer getAcceptStatus() {
        return acceptStatus;
    }

    /**
     * 0：请求被拒绝，1：请求被接受 2:请求还未被处理
     */
    public void setAcceptStatus(Integer acceptStatus) {
        this.acceptStatus = acceptStatus;
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
        UserFriendApply other = (UserFriendApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getApplyMsg() == null ? other.getApplyMsg() == null : this.getApplyMsg().equals(other.getApplyMsg()))
            && (this.getAcceptStatus() == null ? other.getAcceptStatus() == null : this.getAcceptStatus().equals(other.getAcceptStatus()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getApplyMsg() == null) ? 0 : getApplyMsg().hashCode());
        result = prime * result + ((getAcceptStatus() == null) ? 0 : getAcceptStatus().hashCode());
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
        sb.append(", uid=").append(uid);
        sb.append(", targetId=").append(targetId);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", applyMsg=").append(applyMsg);
        sb.append(", acceptStatus=").append(acceptStatus);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}