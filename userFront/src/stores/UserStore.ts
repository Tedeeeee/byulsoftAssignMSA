import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import type { UserData } from '@/type/UserData'

export const accessToken = 'accessToken';

export const userStore = defineStore('user', () => {
  const user = ref<UserData>({
    memberId: 0,
    memberEmail : '',
    memberName : '',
    memberNickname : '',
    memberPhoneNumber : ''
  });

  const isLoggedIn = computed(() => localStorage.getItem(accessToken));

  const userDataSetting = (userData: UserData) => {
    user.value.memberId = userData.memberId;
    user.value.memberEmail = userData.memberEmail;
    user.value.memberName = userData.memberName;
    user.value.memberNickname = userData.memberNickname;
    user.value.memberPhoneNumber = userData.memberPhoneNumber;
  }

  const userDataReset = () => {
    user.value.memberId = 0;
    user.value.memberEmail = '';
    user.value.memberName = '';
    user.value.memberNickname = '';
    user.value.memberPhoneNumber = '';
  }

  return { user, isLoggedIn, userDataSetting, userDataReset };
})