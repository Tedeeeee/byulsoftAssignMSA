<template>
  <div class="q-pa-md flex justify-center">
    <q-card flat bordered class="q-pa-md" style="max-width: 1000px; width: 900px">
      <div class="title">검색</div>
      <div class="flex justify-center items-center" style="height: 30%">
        <div class="row q-gutter-sm" style="width: 100%">
          <div class="col-3 q-pa-sm">
            <q-select filled v-model="searchType" :options="selectCategory" label="카테고리" @update:model-value="changeSearchText" />
          </div>
          <div v-if="searchType === '제목'" class="col-7 q-pa-sm">
            <q-input standout="bg-teal text-white" v-model="searchText" @keyup.enter="searchPost" label="검색" />
          </div>
          <div v-else class="col-7 q-pa-sm">
            <q-select filled v-model="searchText" @keyup.enter="searchPost" :options="regionOptions" />
          </div>
          <div class="col-1 q-pa-sm">
            <q-btn @click="searchPost" color="primary" icon="search" size="lg" />
          </div>
        </div>
      </div>
    </q-card>
  </div>
</template>

<script setup lang="ts">
import { regionOptions } from '@/type/utilsData';
import { ref } from 'vue'

const searchCondition = defineModel();
const searchText = ref<string>()

const changeSearchText = (newVal: string) => {
  if (newVal === '지역') {
    sea
  }
}

</script>

<style scoped>
</style>