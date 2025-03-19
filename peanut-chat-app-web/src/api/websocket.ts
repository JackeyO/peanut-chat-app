import { ref } from 'vue';
import type {
  ImMsg,
  ImMsgReq
} from '../types';
import {
  MessageReqTypeEnum,
  MessageRespTypeEnum
} from '../types';

/**
 * WebSocket Service
 * 
 * This module handles WebSocket connections for real-time communication.
 * Currently, WebSocket is ONLY used for login requests and receiving notifications.
 * All chat message sending is handled via HTTP POST requests in the chat API module.
 * 
 * Note: WebSocket connections require direct access to the server and cannot use
 * Vite's proxy during development, so we always use the full WS_BASE_URL.
 */

// WebSocket base URL from environment
const WS_BASE_URL = import.meta.env.VITE_WS_BASE_URL || 'ws://localhost:9999';

// WebSocket service
export const useWebSocket = () => {
  const socket = ref<WebSocket | null>(null);
  const connected = ref(false);
  const messageListeners = new Map<number, ((data: any) => void)[]>();

  // Connect to WebSocket server
  const connect = () => {
    return new Promise<void>((resolve, reject) => {
      try {
        // Get stored token
        const token = localStorage.getItem('chat_token');

        // Connect to WebSocket server using environment variable
        const wsUrl = `${WS_BASE_URL}${token ? `?token=${token}` : ''}`;
        console.log('Connecting to WebSocket at:', wsUrl);
        socket.value = new WebSocket(wsUrl);

        socket.value.onopen = () => {
          console.log('WebSocket connected');
          connected.value = true;
          resolve();
        };

        socket.value.onmessage = (event) => {
          try {
            console.log('Raw WebSocket message received:', event.data);
            const message = JSON.parse(event.data) as ImMsg;
            console.log('Parsed WebSocket message:', message);
            handleMessage(message);
          } catch (error) {
            console.error('Error parsing WebSocket message:', error, 'Raw data:', event.data);
          }
        };

        socket.value.onerror = (error) => {
          console.error('WebSocket error:', error);
          reject(error);
        };

        socket.value.onclose = (event) => {
          console.log('WebSocket disconnected', event.code, event.reason);
          connected.value = false;
        };
      } catch (error) {
        reject(error);
      }
    });
  };

  // Send message to server
  const sendMessage = <T>(message: ImMsgReq<T>) => {
    if (socket.value && connected.value) {
      socket.value.send(JSON.stringify(message));
    } else {
      console.error('WebSocket not connected');
    }
  };

  // Send login request
  const sendLoginRequest = () => {
    const message: ImMsgReq = {
      type: MessageReqTypeEnum.LOGIN_REQUEST,
      // The backend only expects the type, no data is needed for QR login request
    };
    console.log('Sending login request:', message);
    sendMessage(message);
  };

  // Register event listener for specific message type
  const onMessage = (type: number, callback: (data: any) => void) => {
    if (!messageListeners.has(type)) {
      messageListeners.set(type, []);
    }
    messageListeners.get(type)?.push(callback);
  };

  // Handle incoming messages
  const handleMessage = (message: ImMsg) => {
    console.log('Received message:', message);

    const listeners = messageListeners.get(message.type);
    if (listeners && listeners.length > 0) {
      for (const listener of listeners) {
        listener(message.data);
      }
    }
  };

  // Disconnect WebSocket
  const disconnect = () => {
    if (socket.value) {
      socket.value.close();
      socket.value = null;
      connected.value = false;
    }
  };

  return {
    connect,
    disconnect,
    sendMessage,
    sendLoginRequest,
    onMessage,
    connected
  };
};

// Export message type enums for use in other files
export { MessageReqTypeEnum, MessageRespTypeEnum };

// Create a singleton instance
const websocketService = useWebSocket();
export default websocketService; 