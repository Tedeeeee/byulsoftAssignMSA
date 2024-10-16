<template>
  <div>
    <q-dialog v-model="ModalStore().modalState">
      <q-card>
        <q-card-section>
          <div class="text-h6">{{message}}</div>
        </q-card-section>
        <q-card-actions align="center">
          <q-btn flat :label="labelMessage" @click="ModalStore().closeModal()" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup lang="ts">
import { ModalStore } from '@/stores/ModalStore'

defineProps<{
  labelMessage: string
  message: string
}>();

</script>

<style scoped>
</style>
