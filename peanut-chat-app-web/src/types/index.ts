/**
 * Centralized type definitions for the application
 */

// Common types
export interface BaseResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

// User types
export interface UserVO {
  id: number;
  nickname?: string;
  nickName?: string;
  avatar: string;
}

// Pagination types
export interface CursorPageVo<T> {
  cursor: string;
  pageSize: number;
  recordSize: number;
  isLast: boolean;
  records: T[];
}

// Message types
export enum MessageType {
  TEXT = 0,
  SOUND = 1,
  VIDEO = 2,
  IMAGE = 3,
  EMOJI = 3
}

export interface MessageRequestDto {
  type: number;
  roomId: number;
  body: any;
}

export interface TextMessageBody {
  fromUid: number;
  roomId: number;
  type: number;
  content: string;
}

export interface MessageCursorPageDto {
  roomId: number;
  pageSize: number;
  cursor?: string;
}

export interface ChatMessageVo {
  message: {
    id: number;
    fromUid: number;
    roomId: number;
    type: number;
    content: string;
    sendTime: string;
    updateTime: string;
    status: number;
  };
  messageMarkVo: {
    likes: number;
    disLikes: number;
    currentUserLike: number;
    currentUserDisLike: number;
  };
  content?: string; // For text messages
}

// WebSocket message types
export enum MessageReqTypeEnum {
  LOGIN_REQUEST = 0
}

export enum MessageRespTypeEnum {
  TEXT = 0,
  SOUND = 1,
  VIDEO = 2,
  IMAGE = 3,
  EMOJI = 3,
  RECALL = 4,
  SYSTEM = 5,
  CHAT_MESSAGE = 6,
  SCAN_SUCCESS = 7,
  LOGIN_SUCCESS = 8,
  LOGIN_QR_CODE = 9,
  FRIEND_APPLY = 10
}

export interface ImMsgReq<T = any> {
  type: number;
  data?: T;
}

export interface ImMsg<T = any> {
  type: number;
  data: T;
}

export interface LoginQrCodeMessageVo {
  ticket: string;
  url: string;
  expireSeconds: number;
}

export interface LoginMessageVo {
  token: string;
  userVO: UserVO;
}

export interface ScanMessageVo {
  success: boolean;
} 