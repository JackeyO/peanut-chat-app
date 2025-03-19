<template>
  <div class="chat-container">
    <!-- Sidebar -->
    <div class="sidebar">
      <!-- User profile -->
      <div class="user-profile">
        <div class="avatar-container">
          <el-avatar :size="50" :src="userStore.user?.avatar || defaultAvatar"></el-avatar>
          <div class="online-status"></div>
        </div>
        <div class="user-info">
          <h3>{{ userStore.user?.nickname || userStore.user?.nickName || 'User' }}</h3>
          <span class="status-text">在线</span>
        </div>
      </div>
      
      <!-- Navigation tabs -->
      <div class="nav-tabs">
        <div 
          class="nav-tab" 
          :class="{'active': activeTab === 'messages'}" 
          @click="switchTab('messages')"
        >
          <el-icon><ChatDotRound /></el-icon>
        </div>
        <div 
          class="nav-tab" 
          :class="{'active': activeTab === 'contacts'}" 
          @click="switchTab('contacts')"
        >
          <el-icon><User /></el-icon>
        </div>
        <div 
          class="nav-tab" 
          :class="{'active': activeTab === 'groups'}" 
          @click="switchTab('groups')"
        >
          <el-icon><UserFilled /></el-icon>
        </div>
        <div 
          class="nav-tab" 
          :class="{'active': activeTab === 'favorites'}" 
          @click="switchTab('favorites')"
        >
          <el-icon><Star /></el-icon>
        </div>
      </div>

      <!-- Bottom menu -->
      <div class="bottom-menu">
        <el-tooltip content="设置" placement="right">
          <div class="nav-tab">
            <el-icon><Setting /></el-icon>
          </div>
        </el-tooltip>
        <el-tooltip content="退出登录" placement="right">
          <div class="nav-tab logout" @click="logout">
            <el-icon><SwitchButton /></el-icon>
          </div>
        </el-tooltip>
      </div>
    </div>

    <!-- Main content -->
    <div class="main-content">
      <!-- Debug panel (new) -->
      <div v-if="showDebugInfo" class="debug-panel">
        <h4>Debug Information</h4>
        <div class="debug-info-content">
          <p><strong>Token:</strong> {{ userStore.token }}</p>
          <p><strong>User Info:</strong></p>
          <pre>{{ JSON.stringify(userStore.user, null, 2) }}</pre>
        </div>
        <el-button size="small" type="primary" @click="showDebugInfo = false">
          Hide Debug Info
        </el-button>
      </div>
      
      <!-- Chat list panel -->
      <div class="chat-list-panel">
        <div class="search-bar">
          <el-input 
            v-model="searchQuery" 
            placeholder="搜索" 
            prefix-icon="Search"
            clearable
          />
        </div>
        
        <!-- Conversations list -->
        <div class="conversations-list">
          <div 
            v-for="(chat, index) in filteredChats" 
            :key="index" 
            class="chat-item"
            :class="{'active': selectedChatIndex === index}"
            @click="selectChat(index)"
          >
            <el-avatar :size="40" :src="chat.avatar"></el-avatar>
            <div class="chat-info">
              <div class="chat-header">
                <span class="chat-title">{{ chat.name }}</span>
                <span class="chat-time">{{ chat.lastMessageTime }}</span>
              </div>
              <div class="chat-message">{{ chat.lastMessage }}</div>
            </div>
          </div>
          
          <div v-if="filteredChats.length === 0" class="empty-list">
            <p>暂无聊天记录</p>
          </div>
        </div>
      </div>

      <!-- Chat detail panel -->
      <div class="chat-detail-panel">
        <template v-if="selectedChat">
          <!-- Chat header -->
          <div class="chat-detail-header">
            <h3>{{ selectedChat.name }}</h3>
            <div class="header-actions">
              <el-button 
                size="small" 
                type="primary" 
                plain
                @click="toggleDebugInfo"
                style="margin-right: 10px;"
              >
                {{ showDebugInfo ? 'Hide Debug' : 'Show Debug' }}
              </el-button>
              <el-icon><More /></el-icon>
            </div>
          </div>
          
          <!-- Messages area -->
          <div class="messages-area" ref="messagesContainer" @scroll="handleScroll">
            <div v-if="isLoadingMessages" class="loading-indicator">
              <el-icon class="loading-icon"><Loading /></el-icon>
              <span>加载消息中...</span>
            </div>
            
            <div 
              v-for="(message, index) in selectedChat.messages" 
              :key="index"
              class="message"
              :class="{'my-message': message.fromMe}"
            >
              <div class="message-avatar">
                <el-avatar :size="40" :src="message.fromMe ? (userStore.user?.avatar || defaultAvatar) : selectedChat.avatar"></el-avatar>
              </div>
              <div class="message-content">
                <div class="message-bubble">{{ message.content }}</div>
                <div class="message-footer">
                  <div class="message-time">{{ message.time }}</div>
                  <div class="message-actions">
                    <div 
                      class="action-button like" 
                      :class="{ 'active': message.hasLiked }"
                      @click.stop="toggleLike(message)"
                    >
                      <el-icon><CaretTop /></el-icon>
                      <span v-if="message.likes > 0">{{ message.likes }}</span>
                    </div>
                    <div 
                      class="action-button dislike" 
                      :class="{ 'active': message.hasDisliked }"
                      @click.stop="toggleDislike(message)"
                    >
                      <el-icon><CaretBottom /></el-icon>
                      <span v-if="message.dislikes > 0">{{ message.dislikes }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div v-if="selectedChat.messages.length === 0 && !isLoadingMessages" class="empty-messages">
              <p>暂无消息</p>
            </div>
          </div>
          
          <!-- Message input area -->
          <div class="message-input-area">
            <div class="input-toolbar">
              <el-icon><Microphone /></el-icon>
              <el-icon><PictureFilled /></el-icon>
              <el-icon><Scissor /></el-icon>
              <el-icon><DocumentAdd /></el-icon>
            </div>
            <div class="input-box">
              <el-input
                v-model="newMessage"
                type="textarea"
                :rows="3"
                placeholder="请输入消息..."
                @keyup.enter.native="sendMessage"
                resize="none"
              />
            </div>
            <div class="send-button">
              <el-button type="primary" :disabled="!newMessage.trim()" @click="sendMessage">发送</el-button>
            </div>
          </div>
        </template>
        
        <div v-else class="empty-chat">
          <div class="empty-chat-content">
            <img src="../assets/logo.svg" alt="Peanut Chat" class="empty-logo" />
            <p>请选择一个聊天</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  ChatDotRound,
  DocumentAdd,
  Loading,
  Microphone,
  More,
  PictureFilled,
  Scissor,
  Setting,
  Star,
  SwitchButton,
  User,
  UserFilled
} from '@element-plus/icons-vue';
import { computed, nextTick, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { getMessagesByRoom, sendTextMessage } from '../api/chat';
import websocketService from '../api/websocket';
import defaultAvatar from '../assets/default-avatar.png';
import { useUserStore } from '../store/user';
import { MessageRespTypeEnum, type ChatMessageVo } from '../types';

const router = useRouter();
const userStore = useUserStore();

// Navigation
const activeTab = ref('messages');
const switchTab = (tab: string) => {
  activeTab.value = tab;
};

// Chat state
const searchQuery = ref('');
const newMessage = ref('');
const selectedChatIndex = ref(-1);
const messagesContainer = ref<HTMLElement | null>(null);
const showDebugInfo = ref(false);
const isLoadingMessages = ref(false);
const currentCursor = ref<string | undefined>(undefined);

// Toggle debug panel
const toggleDebugInfo = () => {
  showDebugInfo.value = !showDebugInfo.value;
};

// Mock data for chats (updated to use the enhanced message type)
const chats = ref([
  {
    id: 1,  // This corresponds to roomId in the backend
    name: '张瑞军',
    avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
    lastMessage: '本周是毕业实习的最后一周，大家可以开始着手准备毕业实习报告了',
    lastMessageTime: '12:07',
    messages: [
      {
        id: 101,
        content: '本周是毕业实习的最后一周，大家可以开始着手准备毕业实习报告了，毕业实习答辩会在毕业实习结束后进行。',
        time: '12:07',
        fromMe: false,
        rawDate: new Date(),
        likes: 0,
        dislikes: 0,
        hasLiked: false,
        hasDisliked: false
      }
    ]
  },
  {
    id: 2,  // This corresponds to roomId in the backend
    name: '王梦浩',
    avatar: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
    lastMessage: '我也太倒霉了',
    lastMessageTime: '12:00',
    messages: [
      {
        id: 201,
        content: '我也太倒霉了',
        time: '12:00',
        fromMe: false,
        rawDate: new Date(),
        likes: 0,
        dislikes: 0,
        hasLiked: false,
        hasDisliked: false
      },
      {
        id: 202,
        content: '怎么了？',
        time: '12:01',
        fromMe: true,
        rawDate: new Date(),
        likes: 0,
        dislikes: 0,
        hasLiked: false,
        hasDisliked: false
      }
    ]
  },
  {
    id: 3,  // This corresponds to roomId in the backend
    name: '管理学院2021级就业群',
    avatar: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
    lastMessage: '请武承德到通知为止',
    lastMessageTime: '昨天',
    messages: [
      {
        id: 301,
        content: '请武承德到通知为止',
        time: '昨天 14:30',
        fromMe: false,
        rawDate: (() => {
          const date = new Date();
          date.setDate(date.getDate() - 1);
          return date;
        })(),
        likes: 0,
        dislikes: 0,
        hasLiked: false,
        hasDisliked: false
      }
    ]
  }
]);

// Computed properties
const filteredChats = computed(() => {
  if (!searchQuery.value) return chats.value;
  
  return chats.value.filter(chat => 
    chat.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    chat.lastMessage.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const selectedChat = computed(() => {
  if (selectedChatIndex.value >= 0 && selectedChatIndex.value < chats.value.length) {
    return chats.value[selectedChatIndex.value];
  }
  return null;
});

// Methods
const selectChat = async (index: number) => {
  selectedChatIndex.value = index;
  
  // Reset cursor for pagination
  currentCursor.value = undefined;
  
  // If we select a chat, load its messages from API
  if (index >= 0 && index < chats.value.length) {
    await loadChatMessages(chats.value[index].id);
  }
  
  // Scroll to bottom of messages
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
    }
  });
};

// Load messages from the backend API
const loadChatMessages = async (roomId: number) => {
  try {
    isLoadingMessages.value = true;
    const response = await getMessagesByRoom(roomId, 20, currentCursor.value);
    
    // Update cursor for next pagination
    currentCursor.value = response.cursor;
    
    // If this is the first load (no cursor was set), replace all messages
    // otherwise, add older messages to the beginning
    if (!currentCursor.value || chats.value[selectedChatIndex.value].messages.length === 0) {
      // Convert API messages to our format
      const messages = response.records.map(msg => ({
        id: msg.message.id,
        content: msg.content || '', // Text messages have content directly
        time: formatMessageTime(new Date(msg.message.sendTime)),
        fromMe: msg.message.fromUid === userStore.user?.id,
        rawDate: new Date(msg.message.sendTime),
        likes: msg.messageMarkVo.likes,
        dislikes: msg.messageMarkVo.disLikes,
        hasLiked: msg.messageMarkVo.currentUserLike === 1,
        hasDisliked: msg.messageMarkVo.currentUserDisLike === 1
      }));
      
      // Reverse to show newest at the bottom
      chats.value[selectedChatIndex.value].messages = messages.reverse();
    } else {
      // Add older messages at the beginning
      const olderMessages = response.records.map(msg => ({
        id: msg.message.id,
        content: msg.content || '',
        time: formatMessageTime(new Date(msg.message.sendTime)),
        fromMe: msg.message.fromUid === userStore.user?.id,
        rawDate: new Date(msg.message.sendTime),
        likes: msg.messageMarkVo.likes,
        dislikes: msg.messageMarkVo.disLikes,
        hasLiked: msg.messageMarkVo.currentUserLike === 1,
        hasDisliked: msg.messageMarkVo.currentUserDisLike === 1
      })).reverse();
      
      chats.value[selectedChatIndex.value].messages = [
        ...olderMessages,
        ...chats.value[selectedChatIndex.value].messages
      ];
    }
  } catch (error) {
    console.error('Error loading chat messages:', error);
  } finally {
    isLoadingMessages.value = false;
  }
};

// Load more messages when user scrolls to top
const loadMoreMessages = async () => {
  if (selectedChatIndex.value >= 0 && 
      messagesContainer.value && 
      messagesContainer.value.scrollTop < 50 && 
      !isLoadingMessages.value) {
    await loadChatMessages(chats.value[selectedChatIndex.value].id);
  }
};

// Send a new message
const sendMessage = async () => {
  if (!newMessage.value.trim() || selectedChatIndex.value < 0) return;
  
  // Add message to UI immediately for better UX
  const messageToSend = {
    id: 0, // This will be updated by the backend
    content: newMessage.value.trim(),
    time: new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' }),
    fromMe: true,
    rawDate: new Date(),
    likes: 0,
    dislikes: 0,
    hasLiked: false,
    hasDisliked: false
  };
  
  // Add message to chat
  chats.value[selectedChatIndex.value].messages.push(messageToSend);
  
  // Update last message
  chats.value[selectedChatIndex.value].lastMessage = messageToSend.content;
  chats.value[selectedChatIndex.value].lastMessageTime = messageToSend.time;
  
  // Clear input
  const content = newMessage.value.trim();
  newMessage.value = '';
  
  // Scroll to bottom
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
    }
  });
  
  // Send message to backend using HTTP POST
  try {
    if (userStore.user?.id) {
      const roomId = chats.value[selectedChatIndex.value].id;
      
      // Store user ID in localStorage for future use
      localStorage.setItem('user_id', userStore.user.id.toString());
      
      // Use API to send message via HTTP POST
      await sendTextMessage(
        roomId,
        content,
        userStore.user.id
      );
      
      // No longer sending via WebSocket as per requirements
      // websocketService.sendChatMessage(roomId, content);
    }
  } catch (error) {
    console.error('Error sending message to backend:', error);
  }
};

// Listen for incoming chat messages via WebSocket
const setupChatMessageListener = () => {
  websocketService.onMessage(MessageRespTypeEnum.CHAT_MESSAGE, (data: ChatMessageVo) => {
    console.log('Received chat message:', data);
    
    // Find the chat room for this message
    const roomId = data.message.roomId;
    let chatIndex = chats.value.findIndex(chat => chat.id === roomId);
    
    // If we don't have a chat for this room, create one
    if (chatIndex === -1) {
      // This is a new chat - we need to create it
      // In a real application, you'd fetch the room info from an API
      const newChat = {
        id: roomId,
        name: `Room ${roomId}`,
        avatar: defaultAvatar,
        lastMessage: data.content || '',
        lastMessageTime: formatMessageTime(new Date(data.message.sendTime)),
        messages: []
      };
      chats.value.unshift(newChat);
      chatIndex = 0;
    }
    
    // Get sender ID
    const fromUid = data.message.fromUid;
    const isFromMe = fromUid === userStore.user?.id;
    
    // Create message object
    const newMessage = {
      id: data.message.id,
      content: data.content || '',
      time: formatMessageTime(new Date(data.message.sendTime)),
      fromMe: isFromMe,
      rawDate: new Date(data.message.sendTime),
      likes: data.messageMarkVo.likes,
      dislikes: data.messageMarkVo.disLikes,
      hasLiked: data.messageMarkVo.currentUserLike === 1,
      hasDisliked: data.messageMarkVo.currentUserDisLike === 1
    };
    
    // Add to message list if we're currently looking at this chat
    if (selectedChatIndex.value === chatIndex) {
      chats.value[chatIndex].messages.push(newMessage);
      
      // Scroll to bottom
      nextTick(() => {
        if (messagesContainer.value) {
          messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
        }
      });
    }
    
    // Update the chat preview
    chats.value[chatIndex].lastMessage = newMessage.content;
    chats.value[chatIndex].lastMessageTime = newMessage.time;
    
    // Bring this chat to the top if it's not already there
    if (chatIndex > 0) {
      const chat = chats.value.splice(chatIndex, 1)[0];
      chats.value.unshift(chat);
      
      // If we had this chat selected, update the index
      if (selectedChatIndex.value === chatIndex) {
        selectedChatIndex.value = 0;
      }
    }
  });
};

// Handle scroll to load more messages
const handleScroll = () => {
  if (messagesContainer.value) {
    if (messagesContainer.value.scrollTop < 50) {
      loadMoreMessages();
    }
  }
};

// Logout function
const logout = () => {
  userStore.logout();
  websocketService.disconnect();
  router.push('/');
};

// Setup WebSocket listeners and select first chat on mount
onMounted(() => {
  // Setup WebSocket listener for chat messages
  setupChatMessageListener();
  
  // Select first chat by default
  if (chats.value.length > 0) {
    selectChat(0);
  }
  
  // Add scroll listener to load more messages
  if (messagesContainer.value) {
    messagesContainer.value.addEventListener('scroll', handleScroll);
  }
});

// Clean up event listeners
onUnmounted(() => {
  if (messagesContainer.value) {
    messagesContainer.value.removeEventListener('scroll', handleScroll);
  }
});

// Add this after the existing refs
// Enhanced interface for client-side message display
interface EnhancedMessage {
  id: number;         // Message ID from the backend
  content: string;    // Message content
  time: string;       // Formatted time for display
  fromMe: boolean;    // Whether the message is from the current user
  rawDate: Date;      // Raw Date object for internal use
  likes: number;      // Number of likes
  dislikes: number;   // Number of dislikes
  hasLiked: boolean;  // Whether the current user has liked the message
  hasDisliked: boolean; // Whether the current user has disliked the message
}

// Define these helper functions before the main component setup
// Date formatting utilities
const formatMessageTime = (date: Date): string => {
  const now = new Date();
  const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
  const yesterday = new Date(today);
  yesterday.setDate(yesterday.getDate() - 1);
  
  // Check if the date is today
  if (date >= today) {
    return date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' });
  }
  
  // Check if the date is yesterday
  if (date >= yesterday && date < today) {
    return `昨天 ${date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })}`;
  }
  
  // For older dates, return the full date and time
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};

// Toggle like
const toggleLike = (message: EnhancedMessage) => {
  message.hasLiked = !message.hasLiked;
  if (message.hasLiked) {
    message.likes++;
  } else {
    message.likes--;
  }
};

// Toggle dislike
const toggleDislike = (message: EnhancedMessage) => {
  message.hasDisliked = !message.hasDisliked;
  if (message.hasDisliked) {
    message.dislikes++;
  } else {
    message.dislikes--;
  }
};

// TODO 在聊天框中点击头像显示用户信息
// TODO 房间在线人数显示
// TODO 消息已读未读状态显示以及消息已读和未读的人员列表
// TODO 消息置顶
// TODO 消息撤回
// TODO 消息删除
// TODO 消息提醒
// TODO 消息引用
</script>

<style scoped>
.chat-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
  background-color: #f5f5f5;
  width: 100%;
}

/* Sidebar styles */
.sidebar {
  display: flex;
  flex-direction: column;
  width: 60px;
  background-color: #2c2c2c;
  color: white;
  height: 100%;
  flex-shrink: 0;
}

.user-profile {
  padding: 15px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-bottom: 1px solid #3e3e3e;
}

.avatar-container {
  position: relative;
  margin-bottom: 10px;
}

.online-status {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 10px;
  height: 10px;
  background-color: #67c23a;
  border-radius: 50%;
  border: 2px solid #2c2c2c;
}

.user-info {
  text-align: center;
}

.user-info h3 {
  margin: 0;
  font-size: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 55px;
}

.status-text {
  font-size: 10px;
  color: #67c23a;
}

.nav-tabs {
  flex: 1;
  padding-top: 20px;
}

.nav-tab {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.nav-tab:hover {
  background-color: #3e3e3e;
}

.nav-tab.active {
  background-color: #12b7f5;
}

.nav-tab i {
  font-size: 24px;
}

.bottom-menu {
  margin-top: auto;
  padding-bottom: 20px;
}

.logout {
  color: #f56c6c;
}

/* Main content styles */
.main-content {
  display: flex;
  flex: 1;
  overflow: hidden;
  width: calc(100% - 60px);
}

/* Chat list panel */
.chat-list-panel {
  width: 300px;
  min-width: 300px;
  background-color: #fff;
  border-right: 1px solid #e0e0e0;
  display: flex;
  flex-direction: column;
}

.search-bar {
  padding: 15px 10px;
  border-bottom: 1px solid #e0e0e0;
}

.conversations-list {
  flex: 1;
  overflow-y: auto;
}

.chat-item {
  display: flex;
  padding: 10px 15px;
  border-bottom: 1px solid #f5f5f5;
  cursor: pointer;
  transition: background-color 0.3s;
}

.chat-item:hover {
  background-color: #f9f9f9;
}

.chat-item.active {
  background-color: #e6f7ff;
}

.chat-info {
  margin-left: 10px;
  flex: 1;
  overflow: hidden;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.chat-title {
  font-weight: bold;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.chat-time {
  font-size: 12px;
  color: #909399;
  white-space: nowrap;
}

.chat-message {
  font-size: 13px;
  color: #606266;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.empty-list {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  color: #909399;
}

/* Chat detail panel */
.chat-detail-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  max-width: calc(100% - 300px);
  min-width: 500px;
  overflow: hidden;
}

.chat-detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #e0e0e0;
  background-color: #fff;
}

.chat-detail-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
}

/* Container for a specific chat message from Zhang Ruijun */
.specific-message {
  text-align: left;
  max-width: 100%;
  line-height: 1.5;
}

.header-actions {
  display: flex;
  align-items: center;
}

.header-actions .el-icon {
  margin-left: 5px;
  cursor: pointer;
}

.messages-area {
  flex: 1;
  padding: 20px 40px;
  overflow-y: auto;
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  width: 100%;
}

/* Improve spacing of messages */
.message {
  display: flex;
  margin-bottom: 20px;
  width: 100%;
  max-width: 100%;
  align-items: flex-start;
}

.my-message {
  flex-direction: row-reverse;
}

.message-avatar {
  margin: 0 10px;
  flex-shrink: 0;
}

.message-content {
  max-width: 80%;
  display: flex;
  flex-direction: column;
  min-width: 100px;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: 12px;
  background-color: #fff;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  word-break: break-word;
  width: auto;
  min-width: 100px;
  max-width: 100%;
  text-align: left;
  line-height: 1.4;
  white-space: pre-wrap;
}

.my-message .message-bubble {
  background-color: #12b7f5;
  color: white;
  text-align: left;
}

.message-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 5px;
  width: 100%;
}

.message-time {
  font-size: 12px;
  color: #909399;
  flex-shrink: 0;
}

.my-message .message-time {
  text-align: right;
}

.message-actions {
  display: flex;
  gap: 10px;
  margin-left: 15px;
}

.action-button {
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: color 0.3s;
  color: #909399;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 12px;
}

.action-button:hover {
  color: #12b7f5;
  background-color: #f5f7fa;
}

.action-button.active {
  color: #12b7f5;
}

.action-button span {
  margin-left: 2px;
}

.action-button.like.active {
  color: #67c23a;
}

.action-button.dislike.active {
  color: #f56c6c;
}

.empty-messages {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  color: #909399;
}

.message-input-area {
  padding: 10px 20px;
  background-color: #fff;
  border-top: 1px solid #e0e0e0;
}

.input-toolbar {
  display: flex;
  gap: 15px;
  margin-bottom: 10px;
}

.input-toolbar i {
  cursor: pointer;
  color: #606266;
}

.input-toolbar i:hover {
  color: #12b7f5;
}

.input-box {
  margin-bottom: 10px;
}

.send-button {
  display: flex;
  justify-content: flex-end;
}

.empty-chat {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.empty-chat-content {
  text-align: center;
  color: #909399;
}

.empty-logo {
  width: 100px;
  height: 100px;
  margin-bottom: 20px;
  opacity: 0.5;
}

/* Debug panel styles */
.debug-panel {
  background-color: #f5f5f5;
  border-bottom: 1px solid #e4e7ed;
  padding: 10px;
  margin-bottom: 10px;
  overflow: auto;
  max-height: 200px;
}

.debug-info-content {
  background-color: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 10px;
  font-size: 12px;
  max-height: 150px;
  overflow: auto;
}

.messages-container {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  max-width: 100%;
}

.message {
  margin-bottom: 16px;
  max-width: 70%;
  word-wrap: break-word;
}

/* Loading indicator styles */
.loading-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  color: #909399;
}

.loading-indicator .loading-icon {
  margin-right: 8px;
  font-size: 16px;
  animation: rotate 1s linear infinite;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style> 