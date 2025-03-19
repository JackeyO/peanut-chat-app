package com.sici.chat.aggregator;

import org.springframework.stereotype.Component;

import com.sici.chat.model.chat.message.bo.aggregate.LoginMessageAggregateParam;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.user.vo.UserVO;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import com.sici.utils.bean.ConvertBeanUtil;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.aggregator
 * @author: 20148
 * @description: 登录消息聚合器
 * @create-date: 12/6/2024 3:29 PM
 * @version: 1.0
 */

@Component
public class LoginMessageAggregator extends AbstractMessageAggregator<LoginMessageAggregateParam, LoginMessageVo>{
    @Override
    public MessageRespTypeEnum getSupportedMessageEnum() {
        return MessageRespTypeEnum.LOGIN_SUCCESS;
    }

    @Override
    public LoginMessageVo aggregateAll(LoginMessageAggregateParam loginMessageAggregateParam) {
        LoginMessageVo loginMessageVo = new LoginMessageVo();

        String token = loginMessageAggregateParam.getToken();
        User user = loginMessageAggregateParam.getUser();

        UserVO userVO = ConvertBeanUtil.convertSingle(user, UserVO.class);
        loginMessageVo.setUserVO(userVO);
        loginMessageVo.setToken(token);

        return loginMessageVo;
    }
}
