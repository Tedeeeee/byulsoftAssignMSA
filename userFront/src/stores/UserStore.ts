import { defineStore } from 'pinia'
import { ref } from 'vue';
import type { UserData } from '@/type/UserData'

export const userStore = defineStore('user', () => {
  const user = ref<UserData>({
    memberId: 0,
    memberEmail : '',
    memberPassword : '',
    memberName : '',
    memberNickname : '',
    memberPhoneNumber : ''
  });

  const userDataSetting = (userData: UserData) => {
    user.value.memberId = userData.memberId;
    user.value.memberEmail = userData.memberEmail;
    user.value.memberPassword = userData.memberPassword;
    user.value.memberName = userData.memberName;
    user.value.memberNickname = userData.memberNickname;
    user.value.memberPhoneNumber = userData.memberPhoneNumber;
  }

  return { user, userDataSetting };
})