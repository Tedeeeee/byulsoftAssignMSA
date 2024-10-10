<template>
  <div>
    <q-dialog v-model="ModalStore().modalState">
      <q-card>
        <q-card-section>
          <div class="text-h6">{{message}}</div>
        </q-card-section>
        <q-card-actions align="center">
          <q-btn v-if="message === '수정하시겠습니까?'" flat label="수정하기" @click="closeModal(modalMessage)" color="primary" />
          <q-btn v-if="message === '삭제하시겠습니까?'" flat label="삭제하기" @click="closeModal(modalMessage)" color="primary" />
          <q-btn flat label="닫기" @click="closeModal" color="primary" />
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