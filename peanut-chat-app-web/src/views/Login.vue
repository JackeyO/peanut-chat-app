<template>
  <div class="login-container">
    <!-- Left panel with app information -->
    <div class="app-info-panel">
      <div class="app-logo-container">
        <img src="../assets/logo.svg" alt="Peanut Chat" class="app-logo" />
      </div>
      <h1 class="app-title">Peanut Chat</h1>
      <p class="app-description">安全、快速、便捷的即时通讯应用</p>
      <div class="app-features">
        <div class="feature-item">
          <el-icon class="feature-icon"><Lock /></el-icon>
          <span>端到端加密</span>
        </div>
        <div class="feature-item">
          <el-icon class="feature-icon"><Pointer /></el-icon>
          <span>多端同步</span>
        </div>
        <div class="feature-item">
          <el-icon class="feature-icon"><ChatDotRound /></el-icon>
          <span>群组聊天</span>
        </div>
      </div>
    </div>
    
    <!-- Right panel with login QR code -->
    <div class="login-panel">
      <div class="login-card">
        <h2 class="login-title">欢迎使用 Peanut Chat</h2>
        
        <div class="login-content">
          <div v-if="isConnecting" class="loading-message">
            <el-icon class="loading-icon"><Loading /></el-icon>
            <p>正在连接到服务器...</p>
          </div>
          
          <div v-else-if="!qrCodeInfo" class="loading-message">
            <el-icon class="loading-icon"><Loading /></el-icon>
            <p>获取登录二维码中...</p>
            <el-button type="primary" @click="debugRetryLogin" size="small" style="margin-top: 10px">
              重试
            </el-button>
          </div>
          
          <div v-else class="qrcode-container">
            <div v-if="isScanSuccess" class="scan-success">
              <div class="success-icon-container">
                <el-icon class="scan-success-icon"><Check /></el-icon>
              </div>
              <p class="success-message">扫码成功！</p>
              <p class="success-description">请在手机上确认登录</p>
            </div>
            <div v-else class="qrcode-display">
              <div class="qrcode-wrapper">
                <img :src="qrCodeImageUrl" alt="微信登录二维码" class="qrcode-image" />
              </div>
              <p class="qrcode-tip">请使用微信扫一扫登录</p>
              <p class="qrcode-expire">二维码 <span class="expire-time">{{ qrCodeExpireTime }}</span> 秒后过期</p>
            </div>
          </div>
        </div>
        
        <!-- Debug status section -->
        <div class="debug-status">
          <div class="status-header" @click="toggleDebugPanel">
            调试信息 <el-icon class="toggle-icon"><ArrowDown :class="{ 'is-rotate': showDebugPanel }" /></el-icon>
          </div>
          
          <div v-show="showDebugPanel" class="debug-content">
            <div class="status-row">
              <span>Socket 状态: </span>
              <el-tag :type="websocketService.connected ? 'success' : 'danger'" size="small">
                {{ websocketService.connected ? '已连接' : '未连接' }}
              </el-tag>
              <el-button 
                v-if="!websocketService.connected" 
                type="primary" 
                size="small" 
                @click="reconnectWebSocket"
                style="margin-left: 10px"
              >
                重新连接
              </el-button>
            </div>
            
            <div class="status-row">
              <el-button type="primary" size="small" @click="debugRetryLogin" style="margin-top: 10px">
                重新请求登录码
              </el-button>
            </div>
            
            <div v-if="qrCodeInfo" class="debug-info">
              <p><strong>Ticket:</strong> {{ qrCodeInfo.ticket }}</p>
              <p><strong>URL:</strong> {{ qrCodeInfo.url }}</p>
              <p><strong>QR图片链接:</strong> {{ qrCodeImageUrl }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ArrowDown, ChatDotRound, Check, Loading, Lock, Pointer } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { computed, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import websocketService from '../api/websocket';
import { useUserStore } from '../store/user';
import { MessageRespTypeEnum, type LoginQrCodeMessageVo } from '../types';

const router = useRouter();
const userStore = useUserStore();

const isConnecting = ref(true);
const qrCodeInfo = ref<LoginQrCodeMessageVo | null>(null);
const isScanSuccess = ref(false);
const qrCodeExpireTime = ref(0);
const showDebugPanel = ref(false);
let countdownTimer: number | null = null;

const toggleDebugPanel = () => {
  showDebugPanel.value = !showDebugPanel.value;
};

const qrCodeImageUrl = computed(() => {
  if (!qrCodeInfo.value?.ticket) return '';
  return `https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${encodeURIComponent(qrCodeInfo.value.ticket)}`;
});

// Connection and login flow
onMounted(async () => {
  try {
    // Connect to WebSocket
    await websocketService.connect();
    isConnecting.value = false;

    // Check if we have a stored token
    if (userStore.restoreSession()) {
      // We have a token, try to use it
      console.log('Found stored token, attempting to reconnect');
      // The WebSocket handler will check the token and reconnect if valid
    } else {
      // Send login request to get QR code
      websocketService.sendLoginRequest();
    }

    // Set up listeners for response types
    setupMessageListeners();
  } catch (err) {
    console.error('Failed to connect to WebSocket server', err);
    isConnecting.value = false;
    ElMessage.error('连接服务器失败，请检查网络连接并刷新页面');
  }
});

onUnmounted(() => {
  if (countdownTimer) {
    window.clearInterval(countdownTimer);
  }
});

const setupMessageListeners = () => {
  // Clear any existing listeners first to avoid duplicates
  console.log('Setting up WebSocket message listeners...');
  
  // Listen for QR code message
  websocketService.onMessage(MessageRespTypeEnum.LOGIN_QR_CODE, (data: LoginQrCodeMessageVo) => {
    console.log('Received QR code message:', data);
    
    // Check if we received a valid ticket
    if (!data.ticket) {
      console.error('Received QR code message without ticket:', data);
      ElMessage.error('获取二维码失败，请重试');
      return;
    }
    
    qrCodeInfo.value = data;
    qrCodeExpireTime.value = data.expireSeconds;
    
    console.log('QR code image URL:', qrCodeImageUrl.value);
    
    // Start countdown timer
    if (countdownTimer) {
      window.clearInterval(countdownTimer);
    }
    
    countdownTimer = window.setInterval(() => {
      if (qrCodeExpireTime.value > 0) {
        qrCodeExpireTime.value--;
      } else {
        if (countdownTimer) {
          window.clearInterval(countdownTimer);
        }
        // QR code expired, request a new one
        console.log('QR code expired, requesting a new one...');
        websocketService.sendLoginRequest();
      }
    }, 1000);
  });

  // Listen for scan success message
  websocketService.onMessage(MessageRespTypeEnum.SCAN_SUCCESS, (data) => {
    console.log('Received scan success message:', data);
    isScanSuccess.value = true;
  });

  // Listen for login success message
  websocketService.onMessage(MessageRespTypeEnum.LOGIN_SUCCESS, (data) => {
    console.log('Received login success message:', data);
    // Store user data
    userStore.setUserInfo(data.token, data.userVO);
    
    // Navigate to chat
    router.push('/chat');
  });
  
  console.log('WebSocket message listeners setup complete');
};

const debugRetryLogin = () => {
  console.log('Manually retrying login request...');
  if (!websocketService.connected) {
    console.log('WebSocket not connected, reconnecting first...');
    reconnectWebSocket().then(() => {
      websocketService.sendLoginRequest();
    });
  } else {
    websocketService.sendLoginRequest();
  }
};

const reconnectWebSocket = async () => {
  try {
    isConnecting.value = true;
    console.log('Attempting to reconnect WebSocket...');
    await websocketService.connect();
    isConnecting.value = false;
    console.log('WebSocket reconnected successfully');
    return true;
  } catch (err) {
    console.error('Failed to reconnect to WebSocket server', err);
    isConnecting.value = false;
    ElMessage.error('重新连接失败，请检查网络连接');
    return false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  height: 100vh;
  width: 100%;
  background-color: #f0f2f5;
}

/* Left panel styles */
.app-info-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #12b7f5 0%, #0880d7 100%);
  color: white;
  padding: 40px;
  position: relative;
  overflow: hidden;
}

.app-logo-container {
  margin-bottom: 24px;
}

.app-logo {
  width: 120px;
  height: 120px;
  filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.2));
}

.app-title {
  font-size: 2.4rem;
  font-weight: 700;
  margin-bottom: 16px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.app-description {
  font-size: 1.2rem;
  margin-bottom: 32px;
  text-align: center;
  max-width: 320px;
  opacity: 0.9;
}

.app-features {
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 80%;
  max-width: 300px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 1rem;
  background-color: rgba(255, 255, 255, 0.15);
  padding: 12px 16px;
  border-radius: 8px;
  backdrop-filter: blur(5px);
}

.feature-icon {
  font-size: 1.2rem;
}

/* Right panel styles */
.login-panel {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.login-card {
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 440px;
  overflow: hidden;
}

.login-title {
  font-size: 1.5rem;
  text-align: center;
  margin: 0;
  padding: 24px;
  color: #303133;
  font-weight: 600;
  border-bottom: 1px solid #f0f0f0;
}

.login-content {
  padding: 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 320px;
  justify-content: center;
}

.loading-message {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.loading-icon {
  font-size: 48px;
  margin-bottom: 24px;
  color: #12b7f5;
  animation: rotate 1.5s linear infinite;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.qrcode-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.qrcode-wrapper {
  padding: 16px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  margin-bottom: 20px;
}

.qrcode-image {
  width: 200px;
  height: 200px;
  object-fit: contain;
}

.qrcode-tip {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #303133;
}

.qrcode-expire {
  font-size: 14px;
  color: #909399;
}

.expire-time {
  color: #ff6b6b;
  font-weight: 500;
}

.scan-success {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.success-icon-container {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background-color: #67c23a;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 24px;
}

.scan-success-icon {
  font-size: 48px;
  color: white;
}

.success-message {
  font-size: 24px;
  font-weight: 600;
  color: #67c23a;
  margin-bottom: 8px;
}

.success-description {
  font-size: 16px;
  color: #606266;
}

/* Debug panel styles */
.debug-status {
  border-top: 1px solid #f0f0f0;
  overflow: hidden;
}

.status-header {
  padding: 12px 16px;
  font-size: 14px;
  color: #909399;
  cursor: pointer;
  user-select: none;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}

.status-header:hover {
  background-color: #f5f7fa;
}

.toggle-icon {
  margin-left: 8px;
  transition: transform 0.3s ease;
}

.toggle-icon.is-rotate {
  transform: rotate(180deg);
}

.debug-content {
  padding: 16px;
  background-color: #f5f7fa;
  font-size: 14px;
}

.status-row {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 8px;
}

.debug-info {
  margin-top: 16px;
  padding: 12px;
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #e4e7ed;
}

/* Responsive styles */
@media (max-width: 768px) {
  .login-container {
    flex-direction: column;
  }
  
  .app-info-panel {
    flex: none;
    padding: 24px;
    min-height: 240px;
  }
  
  .app-logo {
    width: 80px;
    height: 80px;
  }
  
  .app-features {
    display: none;
  }
  
  .login-panel {
    flex: none;
    padding: 16px;
  }
  
  .login-content {
    padding: 24px 16px;
    min-height: 280px;
  }
}
</style> 