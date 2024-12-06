package com.sici.chat.aggregator;

import com.sici.chat.model.chat.message.bo.aggregate.ScanMessageAggregateParam;
import com.sici.chat.model.chat.message.vo.ScanMessageVo;
import com.sici.common.enums.chat.message.MessageTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 扫码消息聚合器
 * @create-date: 12/6/2024 3:30 PM
 * @version: 1.0
 */

@Component
public class ScanMessageAggregator extends AbstractMessageAggregator<ScanMessageAggregateParam, ScanMessageVo> {
    @Override
    public MessageTypeEnum getSupportedMessageEnum() {
        return MessageTypeEnum.SCAN_SUCCESS;
    }

    @Override
    public ScanMessageVo aggregateAll(ScanMessageAggregateParam scanMessageAggregateParam) {
        ScanMessageVo scanMessageVo = new ScanMessageVo();
        scanMessageVo.setScanSuccess(scanMessageAggregateParam.getSuccess());
        return scanMessageVo;
    }
}
