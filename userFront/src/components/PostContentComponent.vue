<template>
  <div class="flex items-center">
    <div style="flex: 3; margin-right: 10px">
      <q-select
        v-model="postContents.boardRegion"
        :options="RegionOptions"
        label="지역"
        lazy-rules
        outlined
        :rules="[val => !!val || '지역을 입력해주세요']"
        filled
        class="custom-input"
      />
    </div>
    <div style="flex: 7">
      <q-input
        maxlength="40"
        v-model="postContents.boardTitle"
        label="제목"
        filled
        outlined
        lazy-rules
        :rules="[val => !!val || '제목을 입력해주세요']"
        class="custom-input"
      />
    </div>
  </div>
  <div v-for="(type, idx) in postContents.boardStars" :key="idx">
    <div class="q-gutter-md flex items-center">
      <div class="q-mr-md" style="flex: 2">
        <div class="title">{{ SortOptions[type.boardStarType] }}</div>
      </div>
      <div style="flex: 8">
        <q-rating v-model="type.boardStarRating" max="5" color="amber" icon="star" label="별점" />
      </div>
    </div>
    <q-input maxlength="100" v-model="type.boardStarShortReview" class="contentsText" label="한줄평" filled />
  </div>

  <div class="title">총평</div>
  <q-input
    v-model="postContents.boardContent"
    maxlength="2000"
    class="contentsText"
    label="내용"
    type="textarea"
    rows="10"
    filled
    :rules="[val => !!val || '총평을 입력해주세요']"
  />
</template>

<script setup lang="ts">
import { RegionOptions, SortOptions } from '@/type/UtilsData'

const postContents = defineModel();
</script>

<style scoped>
.title {
  font-size: 24px;
  font-weight: bold;
  margin-top: 10px;
}

.custom-input {
  height: 56px;
  line-height: 1.5;
}
</style>