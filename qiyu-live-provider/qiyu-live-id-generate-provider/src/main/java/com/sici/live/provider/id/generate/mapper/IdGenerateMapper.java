package com.sici.live.provider.id.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.live.model.id.generate.pojo.IdGeneratePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.id.generate.mapper
 * @author: 20148
 * @description:
 * @create-date: 9/13/2024 3:39 PM
 * @version: 1.0
 */

@Mapper
public interface IdGenerateMapper extends BaseMapper<IdGeneratePO> {
}