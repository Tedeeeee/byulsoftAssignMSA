<template>
  <div>
    <q-dialog v-if="ModalStore().modalState">
      <q-card>
        <q-card-section>
          {{message}}
          <div class="text-h6">검색 결과가 존재하지 않습니다</div>
        </q-card-section>
        <q-card-actions align="center">
          <q-btn flat label="검색 초기화" @click="closeModal" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup lang="ts">
import { ModalStore } from '@/stores/ModalState'

defineProps<{
  message: string

}>()

const closeModal = () => {
  ModalStore().closeModal();
}
</script>

<style scoped>
</style>