<template>
  <q-header reveal elevated class="bg-tera text-white" height-hint="98">
    <q-toolbar>
      <q-toolbar-title @click="goHome" class="cursor-pointer">
        <q-avatar>
          <img :src="logo" alt="Logo" />
        </q-avatar>
        요방어때
      </q-toolbar-title>

      <div class="q-pa-md q-gutter-sm">
        <q-btn to="/" color="black" label="목록" />
        <template v-if="userStore().isLoggedIn">
          <q-btn flat :label="userStore().getMemberNickname()" class="custom-link" />
          <q-btn to="/insertPost" color="black" label="글쓰기" />
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
import { userStore } from '@/stores/UserStore'
import { useRouter } from 'vue-router'
import logo from '@/assets/로고.png'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const handleLogout = () => {
  console.log('처리 준비')
}
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