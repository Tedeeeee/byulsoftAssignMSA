import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const LoginStore = defineStore("loginCheck", () => {
  const token = ref('');
  const isLoggedIn = computed(() => !!token.value);

  // 메모리 리셋 -> 그렇다면 리셋을 할때마다 refreshToken 을 할 수 있도록 해야한다.
  const tokenSetting = (accessToken: string) => {
    token.value = accessToken;
  }
  return { isLoggedIn, tokenSetting }
})