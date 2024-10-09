<template>
  <div>
    <q-dialog v-model="isDialogOpen">
      <q-card>
        <q-card-section>
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

</script>

<style scoped>
</style>