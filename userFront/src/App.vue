<template>
  <q-layout view="hHh lpR fff">
  <MainHeader />
  <q-page-container class="q-pa-none">
    <router-view />
  </q-page-container>
  <MainFooter/>
  </q-layout>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import MainFooter from '@/components/MainFooter.vue'
import MainHeader from '@/components/MainHeader.vue'
import { onMounted } from 'vue'
import { getAccessTokenApi } from '@/api/AuthRequiredApi'

const settingToken = async () => {
  try {
    const response = await getAccessTokenApi();
    console.log(response)
    // 토큰 셋팅

    // 사용자 정보 입력하기 -> userApi 가져오기

  } catch (error) {
    console.log(error);
  }
}

onMounted(() => {
  settingToken();
})

</script>

<style scoped>

</style>
