package com.sici.chat.model.chat.room.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * @TableName room
 */
@TableName(value ="room")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间头像
     */
    private String avatar;

    /**
     * 房间描述
     */
    private String description;

    /**
     * 0未删除，1已删除
     */
    private Integer deleteStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 扩展描述
     */
    private String extra;

    /**
     * 0多人群，1：1对1房间
     */
    private Integer type;

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
     * 房间名称
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * 房间名称
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * 房间头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 房间头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 房间描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 房间描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 0未删除，1已删除
     */
    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 0未删除，1已删除
     */
    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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

    /**
     * 扩展描述
     */
    public String getExtra() {
        return extra;
    }

    /**
     * 扩展描述
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }

    /**
     * 0多人群，1：1对1房间
     */
    public Integer getType() {
        return type;
    }

    /**
     * 0多人群，1：1对1房间
     */
    public void setType(Integer type) {
        this.type = type;
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
        Room other = (Room) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoomName() == null ? other.getRoomName() == null : this.getRoomName().equals(other.getRoomName()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getDeleteStatus() == null ? other.getDeleteStatus() == null : this.getDeleteStatus().equals(other.getDeleteStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoomName() == null) ? 0 : getRoomName().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getDeleteStatus() == null) ? 0 : getDeleteStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roomName=").append(roomName);
        sb.append(", avatar=").append(avatar);
        sb.append(", description=").append(description);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", extra=").append(extra);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }

    /**
     * 计算ES搜索评分
     * 评分规则：
     * 1. 基础分数: 50分
     * 2. 房间名称完整度: 有名称+20分，名称长度>=3字符+10分
     * 3. 房间描述完整度: 有描述+15分，描述长度>=10字符+10分
     * 4. 房间头像完整度: 有头像+10分
     * 5. 房间类型权重: 群聊房间+15分，1对1房间+10分
     * 6. 时间新鲜度: 根据创建时间计算，最近7天内创建+0~20分
     * 7. 活跃度: 根据更新时间计算，最近24小时内更新+0~15分
     * 8. 扩展信息完整度: 有扩展信息+5分
     *
     * @return 计算得出的搜索评分 (0-150分)
     */
    public Float calculateEsSearchScore() {
        if (this.deleteStatus != null && this.deleteStatus == 1) {
            return 0.0f; // 已删除房间评分为0
        }

        float score = 50.0f; // 基础分数

        // 1. 房间名称完整度评分 (最高30分)
        if (this.roomName != null && !this.roomName.trim().isEmpty()) {
            score += 20.0f; // 有名称
            if (this.roomName.trim().length() >= 3) {
                score += 10.0f; // 名称长度合理
            }
        }

        // 2. 房间描述完整度评分 (最高25分)
        if (this.description != null && !this.description.trim().isEmpty()) {
            score += 15.0f; // 有描述
            if (this.description.trim().length() >= 10) {
                score += 10.0f; // 描述详细
            }
        }

        // 3. 房间头像完整度评分 (最高10分)
        if (this.avatar != null && !this.avatar.trim().isEmpty()) {
            score += 10.0f; // 有头像
        }

        // 4. 房间类型权重评分 (最高15分)
        if (this.type != null) {
            if (this.type == 0) {
                score += 15.0f; // 多人群聊，更有搜索价值
            } else if (this.type == 1) {
                score += 10.0f; // 1对1房间
            }
        }

        // 5. 时间新鲜度评分 (最高20分)
        if (this.createTime != null) {
            long createTimeMillis = this.createTime.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long daysDiff = (currentTimeMillis - createTimeMillis) / (1000 * 60 * 60 * 24);

            if (daysDiff <= 1) {
                score += 20.0f; // 1天内创建
            } else if (daysDiff <= 3) {
                score += 15.0f; // 3天内创建
            } else if (daysDiff <= 7) {
                score += 10.0f; // 7天内创建
            } else if (daysDiff <= 30) {
                score += 5.0f;  // 30天内创建
            }
            // 超过30天不加分
        }

        // 6. 活跃度评分 (最高15分)
        if (this.updateTime != null) {
            long updateTimeMillis = this.updateTime.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long hoursDiff = (currentTimeMillis - updateTimeMillis) / (1000 * 60 * 60);

            if (hoursDiff <= 1) {
                score += 15.0f; // 1小时内有更新
            } else if (hoursDiff <= 6) {
                score += 12.0f; // 6小时内有更新
            } else if (hoursDiff <= 24) {
                score += 8.0f;  // 24小时内有更新
            } else if (hoursDiff <= 72) {
                score += 5.0f;  // 3天内有更新
            } else if (hoursDiff <= 168) {
                score += 2.0f;  // 7天内有更新
            }
            // 超过7天不加分
        }

        // 7. 扩展信息完整度评分 (最高5分)
        if (this.extra != null && !this.extra.trim().isEmpty()) {
            score += 5.0f; // 有扩展信息
        }

        // 确保评分在合理范围内
        return Math.min(score, 150.0f);
    }

    /**
     * 计算ES搜索评分 - 带自定义参数版本
     *
     * @param memberCount 房间成员数量
     * @param lastActiveTime 最后活跃时间戳
     * @return 计算得出的搜索评分
     */
    public Float calculateEsSearchScore(Integer memberCount, Long lastActiveTime) {
        Float baseScore = calculateEsSearchScore();

        if (baseScore == 0.0f) {
            return 0.0f; // 已删除房间
        }

        float additionalScore = 0.0f;

        // 成员数量加成 (最高15分)
        if (memberCount != null && memberCount > 0) {
            if (memberCount >= 100) {
                additionalScore += 15.0f; // 超大群
            } else if (memberCount >= 50) {
                additionalScore += 12.0f; // 大群
            } else if (memberCount >= 20) {
                additionalScore += 10.0f; // 中等群
            } else if (memberCount >= 10) {
                additionalScore += 8.0f;  // 小群
            } else if (memberCount >= 3) {
                additionalScore += 5.0f;  // 微群
            } else {
                additionalScore += 2.0f;  // 极小群
            }
        }

        // 最后活跃时间加成 (最高10分)
        if (lastActiveTime != null) {
            long currentTimeMillis = System.currentTimeMillis();
            long minutesDiff = (currentTimeMillis - lastActiveTime) / (1000 * 60);

            if (minutesDiff <= 30) {
                additionalScore += 10.0f; // 30分钟内活跃
            } else if (minutesDiff <= 120) {
                additionalScore += 8.0f;  // 2小时内活跃
            } else if (minutesDiff <= 360) {
                additionalScore += 6.0f;  // 6小时内活跃
            } else if (minutesDiff <= 720) {
                additionalScore += 4.0f;  // 12小时内活跃
            } else if (minutesDiff <= 1440) {
                additionalScore += 2.0f;  // 24小时内活跃
            }
        }

        // 总分限制在200分以内
        return Math.min(baseScore + additionalScore, 200.0f);
    }
}