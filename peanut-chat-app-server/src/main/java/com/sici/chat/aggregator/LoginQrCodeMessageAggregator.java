package com.sici.chat.aggregator;

import com.sici.chat.model.chat.message.bo.aggregate.LoginMessageAggregateParam;
import com.sici.chat.model.chat.message.bo.aggregate.LoginQrCodeMessageAggregateParam;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.chat.message.vo.LoginQrCodeMessageVo;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.user.vo.UserVO;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import com.sici.utils.bean.ConvertBeanUtil;
import org.springframework.stereotype.Component;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 登录消息聚合器
 * @create-date: 12/6/2024 3:29 PM
 * @version: 1.0
 */

@Component
public class LoginQrCodeMessageAggregator extends AbstractMessageAggregator<LoginQrCodeMessageAggregateParam, LoginQrCodeMessageVo>{
    @Override
    public MessageRespTypeEnum getSupportedMessageEnum() {
        return MessageRespTypeEnum.LOGIN_SUCCESS;
    }

    @Override
    public LoginQrCodeMessageVo aggregateAll(LoginQrCodeMessageAggregateParam toAggregateInfo) {
        LoginQrCodeMessageVo loginQrCodeMessageVo = new LoginQrCodeMessageVo();
        loginQrCodeMessageVo.setTicket(toAggregateInfo.getTicket());
        loginQrCodeMessageVo.setUrl(toAggregateInfo.getUrl());
        loginQrCodeMessageVo.setExpireSeconds(toAggregateInfo.getExpireSeconds());
        return loginQrCodeMessageVo;
    }
}
