package com.sici.chat.service.impl.ws;

import static com.sici.chat.config.thread.ThreadPoolConfiguration.CHAT_WS_EXECUTOR;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.builder.ImMsgBuilder;
import com.sici.chat.builder.cache.UserLoginCodeKeyBuilder;
import com.sici.chat.event.UserOfflineEvent;
import com.sici.chat.event.UserOnlineEvent;
import com.sici.chat.model.chat.message.bo.aggregate.LoginMessageAggregateParam;
import com.sici.chat.model.chat.message.bo.aggregate.LoginQrCodeMessageAggregateParam;
import com.sici.chat.model.chat.message.bo.aggregate.ScanMessageAggregateParam;
import com.sici.chat.model.chat.message.vo.LoginQrCodeMessageVo;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.model.ws.bo.ImMsgReq;
import com.sici.chat.model.ws.bo.WsChannelInfo;
import com.sici.chat.service.user.UserService;
import com.sici.chat.service.ws.WebSocketService;
import com.sici.chat.ws.channel.ChannelAttr;
import com.sici.chat.ws.channel.ChannelAttrUtil;
import com.sici.chat.ws.channel.ChannelLocalCache;
import com.sici.framework.redis.RedisUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.ws.service.impl
 * @author: 20148
 * @description: ws
 * @create-date: 12/4/2024 11:34 AM
 * @version: 1.0
 */

@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private MessageViewAdapter messageViewAdapter;
    @Resource
    private UserService userService;
    @Resource
    private WxMpService wxMpService;
    @Resource
    private UserLoginCodeKeyBuilder userLoginCodeKeyBuilder;
    @Resource
    @Qualifier(CHAT_WS_EXECUTOR)
    private ThreadPoolTaskExecutor executor;

    private void sendMsgToChannel(Channel channel, ImMsg imMsg) {
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(imMsg)));
    }

    @Override
    public void sendMsg(ImMsg imMsg, Integer receiverId) {
        executor.execute(() -> {
            List<Channel> channelsToSend = ChannelLocalCache.getChannel(receiverId);
            channelsToSend.forEach(channel -> sendMsgToChannel(channel, imMsg));
        });
    }

    @Override
    public void sendMsg(ImMsg imMsg, List<Integer> receiverId) {
        executor.execute(() -> {
            receiverId.forEach(id -> {
                List<Channel> channelsToSend = ChannelLocalCache.getChannel(id);
                if (channelsToSend != null) {
                    channelsToSend.forEach(channel -> sendMsgToChannel(channel, imMsg));
                }
            });
        });
    }

    /**
     * 用户下线行为
     *
     * @param channel
     */
    @Override
    public void userOffline(Channel channel) {
        ChannelLocalCache.removeOnlineChannelAndInfo(channel);
        Integer userId = ChannelAttrUtil.getAttr(channel, ChannelAttr.USER_ID);
        if (userId != null) {
            applicationEventPublisher.publishEvent(new UserOfflineEvent(this, userId));
        }
    }

    /**
     * 微信扫码成功
     *
     * @param loginCode - 登陆码
     * @return
     */
    @Override
    public Boolean wxScanSuccess(Integer loginCode) {
        Channel waitLoginChannel = ChannelLocalCache.getWaitLoginChannel(loginCode);
        Boolean success = waitLoginChannel != null;
        if (success) {
            sendMsgToChannel(waitLoginChannel,
                    ImMsgBuilder.buildScanMessage(messageViewAdapter.adaptScanMessage(new ScanMessageAggregateParam(true))));
        }
        return success;
    }

    /**
     * 微信授权成功
     *
     * @param loginCode - 登陆码
     * @param user      - 登录用户信息
     * @return
     */
    @Override
    public Boolean wxAuthorizeSuccess(Integer loginCode, User user) {
        String token = userService.createToken(user);
        Channel waitLoginChannel = ChannelLocalCache.getWaitLoginChannel(loginCode);

        log.info("用户授权成功，登陆码:{}, 用户信息:{}, channel:{}, token:{}", loginCode, user, waitLoginChannel, token);

        if (waitLoginChannel != null) {
            // 从等待登录的channel里删除
            ChannelLocalCache.removeWaitLoginChannel(loginCode);

            // 登陆成功
            loginSuccess(waitLoginChannel, user, token);

            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 处理登录请求
     * @param ctx
     */
    @Override
    public void handlerLoginReq(ChannelHandlerContext ctx, ImMsgReq msgReq) {
        // 生成登陆码
        Integer loginCode = generateLoginCode();
        log.info("登录请求，channel:{}, loginCode:{}", ctx.channel(), loginCode);
        try {
            WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(loginCode,
                    (int) userLoginCodeKeyBuilder.getExpireTime().toSeconds());
            LoginQrCodeMessageVo loginQrCodeMessageVo =
                    messageViewAdapter.adaptLoginQrCodeMessage(LoginQrCodeMessageAggregateParam
                            .builder()
                            .ticket(wxMpQrCodeTicket.getTicket())
                            .url(wxMpQrCodeTicket.getUrl())
                            .expireSeconds(wxMpQrCodeTicket.getExpireSeconds())
                            .build());

            // 保存登陆码与channel的关联
            ChannelLocalCache.addWaitLoginChannel(loginCode, ctx.channel());

            log.info("qr-code ticket:{}", loginQrCodeMessageVo.getTicket());
            // 发送给前端，展示二维码
            sendMsgToChannel(ctx.channel(), ImMsgBuilder.buildLoginQrCodeMessage(loginQrCodeMessageVo));
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成登陆码
     *
     * @return
     */
    private Integer generateLoginCode() {
        int loginCode;
        do {
            loginCode = RedisUtils.integerInc(userLoginCodeKeyBuilder.build(null), (int) userLoginCodeKeyBuilder.getExpireTime().toSeconds(),
                    TimeUnit.SECONDS);
        } while (ChannelLocalCache.checkLoginCodeExists(loginCode));
        return loginCode;
    }

    /**
     * token鉴权
     *
     * @param channel
     */
    @Override
    public void tokenAuthorize(Channel channel) {
        String token = ChannelAttrUtil.getAttr(channel, ChannelAttr.TOKEN);

        if (!StringUtils.isEmpty(token)) {
            User user = userService.authorize(token);
            // 登录成功
            if (user != null) {
                loginSuccess(channel, user, token);
            }
        }
    }

    /**
     * 更新本地在线channel信息
     *
     * @param channel
     * @param user
     */
    private void updateOnlineChannel(Channel channel, User user) {
        // 把channel以及其用户信息放到在线信息里
        ChannelLocalCache.addOnlineChannelAndInfo(user.getId(), channel, new WsChannelInfo(user.getId()));
    }

    /**
     * 用户上线行为
     *
     * @param channel
     * @param user
     */
    private void userOnline(Channel channel, User user) {
        // 更新本地在线channel信息
        updateOnlineChannel(channel, user);
        // 发布用户上线事件
        applicationEventPublisher.publishEvent(new UserOnlineEvent(this, user.getId()));
    }

    /**
     * 登录成功，微信授权成功后或者token鉴权成功后调用
     *
     * @param channel
     * @param user
     * @param token
     */
    @Override
    public void loginSuccess(Channel channel, User user, String token) {
        userOnline(channel, user);

        // 告知客户端登陆成功
        sendMsgToChannel(channel,
                ImMsgBuilder.buildLoginMessage(messageViewAdapter.adaptLoginMessage(new LoginMessageAggregateParam(token, user))));
    }

    /**
     * 处理连接请求
     * @param channel
     */
    @Override
    public void connect(Channel channel) {
        ChannelLocalCache.addOnlineChannelInfo(channel, new WsChannelInfo());
    }
}