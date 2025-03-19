import { defineStore } from 'pinia';
import { ref } from 'vue';
import type { UserVO } from '../types';

export const useUserStore = defineStore('user', () => {
  const token = ref<string | null>(null);
  const user = ref<UserVO | null>(null);
  const isLoggedIn = ref(false);

  // Set user and token after successful login
  const setUserInfo = (newToken: string, userInfo: UserVO) => {
    token.value = newToken;
    user.value = userInfo;
    isLoggedIn.value = true;

    // Save token to localStorage for persistence
    localStorage.setItem('chat_token', newToken);
  };

  // Attempt to restore session from localStorage
  const restoreSession = (): boolean => {
    const savedToken = localStorage.getItem('chat_token');
    if (savedToken) {
      token.value = savedToken;
      // Note: We don't have user info yet, it will be loaded when WebSocket connects
      return true;
    }
    return false;
  };

  // Logout user
  const logout = () => {
    token.value = null;
    user.value = null;
    isLoggedIn.value = false;
    localStorage.removeItem('chat_token');
  };

  return {
    token,
    user,
    isLoggedIn,
    setUserInfo,
    restoreSession,
    logout
  };
}); 