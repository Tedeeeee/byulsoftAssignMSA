<template>
  <q-header reveal elevated class="bg-tera text-white" height-hint="98">
    <q-toolbar>
      <q-toolbar-title @click="goHome" class="cursor-pointer">
        <q-avatar>
          <img :src="logo" alt="Logo" />
        </q-avatar>
        요방어때 - 관리자
      </q-toolbar-title>

      {{LoginStore().token}}
      <div class="q-pa-md q-gutter-sm">
        <template v-if="userLoginState">
          <q-btn flat :label="adminStore().admin.adminNickname" to="/myPage" class="custom-link" />
          <q-btn @click="handleLogout" color="black" label="로그아웃" />
        </template>
        <template v-else>
          <q-btn to="/login" color="black" label="로그인" />
          <q-btn to="/signUp" color="black" label="회원가입" />
        </template>
      </div>
    </q-toolbar>
  </q-header>
</template>

<script setup lang="ts">
import { accessToken, adminStore } from '@/stores/AdminStore'
import { useRouter } from 'vue-router'
import logo from '@/assets/로고.png'
import { onMounted, ref } from 'vue'
import { useNotifications } from '@/common/CommonNotify'
import { LoginStore } from '@/stores/LoginStore'

const { positiveNotify } = useNotifications();
const router = useRouter()
const goHome = () => {
  router.push('/')
}

const userLoginState = ref<boolean>(!!localStorage.getItem(accessToken))

const handleLogout = async () => {
  try {
    const response = await logout();
    positiveNotify(response.data.message)
    localStorage.removeItem(accessToken);
    userLoginState.value = false
    adminStore().userDataReset()
    await router.push("/");
  } catch (error) {
    console.log(error)
  }
}

onMounted(async () => {
  if (localStorage.getItem(accessToken)) {
    const response = await getUserData();
    adminStore().adminDataSetting(response.data.body);
  }
})
</script>

<style scoped>
.cursor-pointer {
  cursor: pointer;
}

.custom-link {
  text-decoration: underline;
  color: black;
  cursor: pointer;
}
</style>
