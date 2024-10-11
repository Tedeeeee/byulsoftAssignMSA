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
import { getAccessTokenApi, getUserData } from '@/api/AuthRequiredApi'
import { userStore } from '@/stores/UserStore'
import { onMounted } from 'vue'

const settingToken = async () => {
  try {
    const tokenResponse = await getAccessTokenApi()
    localStorage.setItem('accessToken', tokenResponse.data.message)

    const userResponse = await getUserData()
    userStore().userDataSetting(userResponse.data.body)
    console.log(userStore().user)
  } catch (error) {
    console.log(error)
  }
}

onMounted( async () => {
  const accessToken = localStorage.getItem('accessToken')
  console.log(accessToken)
  if (accessToken) {
    const response = await getUserData();
    userStore().userDataSetting(response.data.body)
  }
  await settingToken();
})
</script>

<style scoped>

</style>
