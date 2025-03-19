/**
 * Centralized API endpoints for the application
 * 
 * This file contains all API endpoints used in the application.
 * Always reference these constants instead of hardcoding URLs in services.
 */

// Chat API endpoints
export const CHAT_API = {
  SEND_MESSAGE: '/peanut/chat/send',
  GET_MESSAGES: '/peanut/chat/msg/page',
  LIKE_MESSAGE: '/peanut/chat/msg/like',
  DISLIKE_MESSAGE: '/peanut/chat/msg/dislike',
  CANCEL_LIKE: '/peanut/chat/msg/cancel-like',
  CANCEL_DISLIKE: '/peanut/chat/msg/cancel-dislike'
};

// User API endpoints
export const USER_API = {
  LOGIN: '/peanut/user/login',
  LOGOUT: '/peanut/user/logout',
  PROFILE: '/peanut/user/profile'
};

// Room API endpoints
export const ROOM_API = {
  ROOM_LIST: '/peanut/room/list',
  ROOM_DETAIL: '/peanut/room/detail',
  CREATE_ROOM: '/peanut/room/create'
};

// WebSocket endpoints
export const WS_API = {
  CONNECT: '' // WebSocket connection is handled via environment variables
}; 