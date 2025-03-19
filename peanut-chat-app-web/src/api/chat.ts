import type {
  ChatMessageVo,
  CursorPageVo,
  MessageCursorPageDto,
  MessageRequestDto,
  TextMessageBody
} from '../types';
import { MessageType as MessageTypeEnum } from '../types';
import http from '../utils/http';
import { CHAT_API } from './api';

/**
 * Chat API Service
 * 
 * This module provides HTTP endpoints for all chat-related operations.
 * Message sending and retrieval are handled via HTTP POST requests,
 * while WebSocket is only used for login and real-time notifications.
 */

/**
 * Send a text message
 */
export const sendTextMessage = async (roomId: number, content: string, fromUid: number): Promise<any> => {
  const messageBody: TextMessageBody = {
    fromUid,
    roomId,
    type: MessageTypeEnum.TEXT,
    content
  };

  const request: MessageRequestDto = {
    type: MessageTypeEnum.TEXT,
    roomId,
    body: messageBody
  };

  try {
    const response = await http.post(CHAT_API.SEND_MESSAGE, request);
    return response;
  } catch (error) {
    console.error('Error sending message:', error);
    throw error;
  }
};

/**
 * Get messages for a room with cursor pagination
 */
export const getMessagesByRoom = async (
  roomId: number,
  pageSize: number = 20,
  cursor?: string
): Promise<CursorPageVo<ChatMessageVo>> => {
  try {
    const requestData: MessageCursorPageDto = {
      roomId,
      pageSize,
      cursor
    };

    const response = await http.post<{ data: CursorPageVo<ChatMessageVo> }>(CHAT_API.GET_MESSAGES, requestData);
    return response.data;
  } catch (error) {
    console.error('Error fetching messages:', error);
    throw error;
  }
};

// TODO 点赞和踩的后台接口已经重构，等待更新前端
/**
 * Like a message
 */
export const likeMessage = async (messageId: number): Promise<any> => {
  try {
    const response = await http.post(CHAT_API.LIKE_MESSAGE, { messageId });
    return response;
  } catch (error) {
    console.error('Error liking message:', error);
    throw error;
  }
};

/**
 * Dislike a message
 */
export const dislikeMessage = async (messageId: number): Promise<any> => {
  try {
    const response = await http.post(CHAT_API.DISLIKE_MESSAGE, { messageId });
    return response;
  } catch (error) {
    console.error('Error disliking message:', error);
    throw error;
  }
};

/**
 * Cancel like on a message
 */
export const cancelLike = async (messageId: number): Promise<any> => {
  try {
    const response = await http.post(CHAT_API.CANCEL_LIKE, { messageId });
    return response;
  } catch (error) {
    console.error('Error canceling like:', error);
    throw error;
  }
};

/**
 * Cancel dislike on a message
 */
export const cancelDislike = async (messageId: number): Promise<any> => {
  try {
    const response = await http.post(CHAT_API.CANCEL_DISLIKE, { messageId });
    return response;
  } catch (error) {
    console.error('Error canceling dislike:', error);
    throw error;
  }
}; 