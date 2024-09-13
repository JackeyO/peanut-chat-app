package com.sici.live.model.id.generate.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("t_id_generate_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdGeneratePO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * id 备注描述
     */
    private String remark;
    /**
     * 初始化值
     */
    private long initNum;
    /**
     * 步长
     */
    private int step;
    /**
     * 是否是有序的 id
     */
    private int isSeq;
    /**
     * 当前 id 所在阶段的开始值
     */
    private long currentStart;
    /**
     * 当前 id 所在阶段的阈值
     */
    private long nextThreshold;
    /**
     * 业务代码前缀
     */
    private String idPrefix;
    /**
     * 乐观锁版本号
     */
    private int version;
    private Date createTime;
    private Date updateTime;
}