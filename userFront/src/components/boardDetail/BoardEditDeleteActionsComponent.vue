<template>
  <div class="button-container q-mt-md">
    <q-btn label="수정" @click="emit('updateModal', '수정하시겠습니까?')" color="primary" class="q-mr-xs" />
    <q-btn label="삭제" @click="emit('updateModal', '삭제하시겠습니까?')" color="negative" class="q-mr-xs" />
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits<{
  (e: 'updateModal', message: string): void;
  (e: 'deleteModal', message: string): void;
}>();
</script>

<style scoped>
.q-page {
  max-width: 1000px;
  margin: 0 auto;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 8px;
}
</style>