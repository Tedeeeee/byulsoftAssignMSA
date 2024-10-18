<template>
  <q-card flat bordered class="q-pa-sm">
    <div class="row">
      <div class="left-section q-mr-sm">
        <q-img :src="image" alt="사진" style="width: 70px; height: 70px" />
      </div>
      <div class="right-section">
        <div class="row q-mt-sm">
          <div class="col">
            <q-rating :model-value="star.boardStarRating" max="5" readonly />
          </div>
        </div>
        <div class="info-section q-mb-sm">
          <h6>{{ star.boardStarShortReview }}</h6>
        </div>
      </div>
    </div>
  </q-card>
</template>

<script setup lang="ts">
import { BoardStarData } from '@/type/BoardData'
defineProps<{
  star: BoardStarData;
  image: string;
}>();
</script>

<style scoped></style>