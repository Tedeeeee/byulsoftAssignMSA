<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card class="my-modal">
      <q-card-section>
        <div class="text-h6">신고하기</div>
        <div class="q-mt-md">
          <div><span style="font-weight: bold">대상자 :</span> {{ memberNickname }}</div>
          <q-separator />
          <div class="q-mt-md">
            <span style="font-weight: bold">사유:</span>
            <q-input
              v-model="reportContent"
              type="textarea"
              rows="1"
              placeholder="신고 사유를 입력하세요"
            />
          </div>
        </div>
      </q-card-section>
      <q-card-actions align="center">
        <q-btn flat label="신고" @click="emit('reportUser', reportContent)" color="primary" />
        <q-btn flat label="닫기" @click="emit('closeModal')" color="primary" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'

defineProps<{
  memberNickname: string
}>()

const emit = defineEmits<{
  (e: 'closeModal'): void
  (e: 'reportUser'): void
}>()

const isOpen = ref<boolean>(true)
const reportContent = ref<string>('');
</script>

<style scoped>
.my-modal {
  width: 500px; /* 원하는 너비로 설정 */
  max-width: 100vw; /* 화면 크기에 따라 최대 너비 설정 */
}
</style>