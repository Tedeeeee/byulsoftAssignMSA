<template>
  <div class="q-pa-md flex justify-center">
    <q-card flat bordered class="q-pa-md" style="max-width: 1000px; width: 900px">
      <div class="title">검색</div>
      <div class="flex justify-center items-center" style="height: 30%">
        <div class="row q-gutter-sm" style="width: 100%">
          <div class="col-3 q-pa-sm">
            <q-select
              filled
              v-model="searchType"
              :options="SelectCategory"
              label="카테고리"
              @update:model-value="changeSearchText"
            />
          </div>
          <div v-if="searchType === SelectCategory[0]" class="col-7 q-pa-sm">
            <q-input
              standout="bg-teal text-white"
              v-model="searchText"
              @keyup.enter="searchPost"
              label="검색"
            />
          </div>
          <div v-else class="col-7 q-pa-sm">
            <q-select
              filled
              v-model="searchText"
              @keyup.enter="searchPost"
              :options="RegionOptions"
            />
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
import { RegionOptions } from '@/type/UtilsData'
import { ref } from 'vue'
import { SelectCategory } from '@/type/UtilsData'

const searchConditionForBoard = defineModel()
const searchText = ref<string>(searchConditionForBoard.value.searchText || '')
const searchType = ref<string>(searchConditionForBoard.value.searchType || SelectCategory[0])

const emit = defineEmits<{
  (e: 'searchPost'): void
}>()

const searchPost = () => {
  searchConditionForBoard.value.searchType = searchType
  searchConditionForBoard.value.searchText = searchText
  emit('searchPost')
}

const changeSearchText = (newVal: string) => {
  if (newVal === SelectCategory[1]) {
    searchText.value = RegionOptions[0]
  } else {
    searchText.value = ''
  }
}
</script>

<style scoped>
.q-page {
  max-width: 1000px;
  margin: 0 auto;
}

.row {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.col-3 {
  flex: 3;
}

.title {
  font-size: 30px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  margin-top: 10px;
}
</style>