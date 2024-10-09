<template>
  <q-layout view="hHh lpR fff">
    <MainHeader />
  <!-- 헤더 설정 -->

  <q-page-container class="q-pa-none">
    <router-view />
  </q-page-container>
    <MainFooter/>
  <!-- 풋터 설정 -->
  </q-layout>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import MainFooter from '@/components/MainFooter.vue'
import MainHeader from '@/components/MainHeader.vue'


</script>

<style scoped>

</style>
