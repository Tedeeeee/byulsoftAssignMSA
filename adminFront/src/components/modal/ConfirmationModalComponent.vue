<template>
  <div>
    <q-dialog v-model="ModalStore().confirmModalState">
      <q-card>
        <q-card-section>
          <div class="text-h6">{{message}}</div>
        </q-card-section>
        <q-card-actions align="center">
          <q-btn flat :label="labelMessage" @click="emit('deleteBoard')" color="primary" />
          <q-btn flat label="닫기" @click="ModalStore().closeConfirmModal()" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup lang="ts">
import { ModalStore } from '@/stores/ModalStore'

defineProps<{
  message: string
  labelMessage: string
}>()

const emit = defineEmits<{
  (e : 'deleteBoard'): void
}>()

</script>

<style scoped>
</style>
