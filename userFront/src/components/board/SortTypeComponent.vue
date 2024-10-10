<template>
  <div class="flex flex-center">
    <q-card flat class="q-pa-md" style="max-width: 1000px; width: 900px">
      <div class="flex flex-center">
        <div class="row q-gutter-sm">
          <template v-for="(label, value) in SortOptions" :key="value">
            <q-btn
              flat
              :label="label + '↑'"
              @click="handleSort(SortOrder[1], value)"
              :color="isActive(SortOrder[1], value) ? 'orange' : 'primary'"
              class="q-mr-xs"
            />
          </template>
        </div>
      </div>
      <div class="flex flex-center">
        <div class="row q-gutter-sm">
          <q-btn
            flat
            :label="label + '↓'"
            @click="handleSort(SortOrder[0], value)"
            :color="isActive(SortOrder[0], value) ? 'orange' : 'primary'"
            class="q-mr-xs"
          />
        </div>
      </div>
    </q-card>
  </div>
</template>

<script setup lang="ts">
import type { SortOrder, SortOptions } from '@/type/UtilsData'
import { onMounted } from 'vue'

const searchBoard = defineModel();
const emit = defineEmits<{
  (e: 'sort'): void;
}>()

const handleSort = (sortOrder: string, sortType: string): void => {
  console.log(searchBoard.value);
  searchBoard.value.sortOrder = sortOrder;
  searchBoard.value.sortType = sortType;
  emit('sort');
}

const isActive = (sortOrder: string, sortType: string): boolean => {
  return searchBoard.value.sortType === sortType && searchBoard.value.sortOrder === sortOrder;
}

onMounted(() => {
  isActive(searchBoard.value.sortType, searchBoard.value.sortOrder);
})
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
</style>