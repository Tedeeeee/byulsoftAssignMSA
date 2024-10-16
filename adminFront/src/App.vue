<template>
  <q-layout view="hHh lpR fff">
    <MainHeader :key="$route.fullPath"/>
    <q-page-container class="q-pa-none">
      <router-view :key="$route.fullPath" />
    </q-page-container>
    <MainFooter/>
  </q-layout>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import MainHeader from '@/components/MainHeader.vue'
import MainFooter from '@/components/MainFooter.vue'
</script>


<style scoped>

</style>
