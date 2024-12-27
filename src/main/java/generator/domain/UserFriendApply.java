package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_friend_apply
 */
@TableName(value ="user_friend_apply")
public class UserFriendApply implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请人id
     */
    private Integer uid;

    /**
     * 申请的目标对象id
     */
    private Integer targetId;

    /**
     * 申请发起时间
     */
    private Date applyTime;

    /**
     * 0：请求被拒绝，1：请求被接受
     */
    private Integer acceptStatus;

    /**
     * 申请消息内容
     */
    private String applyMsg;

    /**
     * 申请是否已经被处理
     */
    private Integer dealStatus;

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
     * 申请人id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 申请人id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 申请的目标对象id
     */
    public Integer getTargetId() {
        return targetId;
    }

    /**
     * 申请的目标对象id
     */
    public void setTargetId(Integer targetId) {
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
     * 0：请求被拒绝，1：请求被接受
     */
    public Integer getAcceptStatus() {
        return acceptStatus;
    }

    /**
     * 0：请求被拒绝，1：请求被接受
     */
    public void setAcceptStatus(Integer acceptStatus) {
        this.acceptStatus = acceptStatus;
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
     * 申请是否已经被处理
     */
    public Integer getDealStatus() {
        return dealStatus;
    }

    /**
     * 申请是否已经被处理
     */
    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
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
            && (this.getAcceptStatus() == null ? other.getAcceptStatus() == null : this.getAcceptStatus().equals(other.getAcceptStatus()))
            && (this.getApplyMsg() == null ? other.getApplyMsg() == null : this.getApplyMsg().equals(other.getApplyMsg()))
            && (this.getDealStatus() == null ? other.getDealStatus() == null : this.getDealStatus().equals(other.getDealStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getAcceptStatus() == null) ? 0 : getAcceptStatus().hashCode());
        result = prime * result + ((getApplyMsg() == null) ? 0 : getApplyMsg().hashCode());
        result = prime * result + ((getDealStatus() == null) ? 0 : getDealStatus().hashCode());
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
        sb.append(", acceptStatus=").append(acceptStatus);
        sb.append(", applyMsg=").append(applyMsg);
        sb.append(", dealStatus=").append(dealStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}