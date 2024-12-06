package com.sici.chat.aggregator;

import com.sici.chat.dao.UserDao;
import com.sici.chat.model.chat.message.bo.aggregate.LoginMessageAggregateParam;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.chat.message.vo.ScanMessageVo;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.user.vo.UserVO;
import com.sici.common.enums.chat.message.MessageTypeEnum;
import com.sici.utils.bean.ConvertBeanUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    public MessageTypeEnum getSupportedMessageEnum() {
        return MessageTypeEnum.LOGIN_SUCCESS;
    }

    @Override
    public LoginMessageVo aggregateAll(LoginMessageAggregateParam loginMessageAggregateParam) {
        LoginMessageVo loginMessageVo = new LoginMessageVo();

        String token = loginMessageAggregateParam.getToken();
        User user = loginMessageAggregateParam.getUser();

        UserVO userVO = ConvertBeanUtil.convertSingle(user, UserVO.class);
        loginMessageVo.setUserVO(userVO);

        return loginMessageVo;
    }
}
