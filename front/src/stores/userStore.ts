import { defineStore } from 'pinia'
import { ref } from 'vue';
import type { UserData } from '@/type/UserData'

export const userStore = defineStore('user', () => {
  const user = ref<UserData>('user');

  return { user };
})