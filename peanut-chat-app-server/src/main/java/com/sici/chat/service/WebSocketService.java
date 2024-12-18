package com.sici.chat.service;

import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.ws.bo.ImMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.ws.service
 * @author: 20148
 * @description: ws相关接口
 * @create-date: 12/4/2024 11:28 AM
 * @version: 1.0
 */

public interface WebSocketService {
    /**
     * 发送消息到单个人
     * @param imMsg
     * @param receiverId
     */
    void sendMsg(ImMsg imMsg, Integer receiverId);

    /**
     * 发送消息到多个人
     * @param imMsg
     * @param receiverId
     */
    void sendMsg(ImMsg imMsg, List<Integer> receiverId);
    /**
     * 用户下线
     * @param channel
     */
    void userOffline(Channel channel);

    /**
     * 扫码成功后WS处理
     * @param loginCode
     */
    Boolean wxScanSuccess(Integer loginCode);

    /**
     * 处理登录请求
     */
    void handlerLoginReq(ChannelHandlerContext ctx);

    /**
     * token鉴权
     * @param channel
     */
    void tokenAuthorize(Channel channel);

    void loginSuccess(Channel channel, User user, String token);

    /**
     * 微信授权成功
     * @param loginCode
     * @param user
     * @return
     */
    Boolean wxAuthorizeSuccess(Integer loginCode, User user);

    /**
     * 处理连接请求
     * @param channel
     */
    void connect(Channel channel);
}