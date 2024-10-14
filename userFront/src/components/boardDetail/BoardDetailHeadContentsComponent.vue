<template>
  <declaration-modal-component v-if="isDeclarationModalOpen" :member-nickname="postHeadData.memberNickname" @report-user="reportPost" @close-modal="isDeclarationModalOpen = false"/>
  <q-card flat bordered class="q-pa-md">
    <div class="row">
      <div class="left-section q-mr-md">
        <div>
        <span style="font-size: 100px; font-weight: bold;">{{ postHeadData.boardId }}</span>
        </div>
        <q-separator vertical/>
        <div>
        <q-btn v-if="userStore().user.memberNickname !== postHeadData.memberNickname" label="신고하기" color="negative" @click="openDeclarationModal" />
      </div>
      </div>

      <q-separator vertical />
      <div class="right-section">
        <div class="info-section q-mb-md">
          <h4>{{ postHeadData.boardTitle }}</h4>
        </div>
        <q-separator />
        <div class="row q-col-gutter-ms q-mt-md">
          <div class="col">
            <span style="font-weight: bold">작성자</span><br />
            <span class="col-2">{{ postHeadData.memberNickname }}</span>
          </div>
          <q-separator vertical />
          <div class="col">
            <span style="font-weight: bold">작성시간</span><br />
            <span class="col-3">{{ postHeadData.boardCreatedAt }}</span>
          </div>
          <q-separator vertical />
          <div class="col">
            <span style="font-weight: bold">지역</span><br />
            <span class="col-3">{{ postHeadData.boardRegion }}</span>
          </div>
        </div>
      </div>
    </div>
  </q-card>
</template>

<script setup lang="ts">
import type { BoardHeaderData } from '@/type/BoardData'
import DeclarationModalComponent from '@/components/modal/DeclarationModalComponent.vue'
import { ref } from 'vue'
import { userStore } from '@/stores/UserStore'

defineProps<{
  postHeadData: BoardHeaderData;
}>();

const isDeclarationModalOpen = ref<boolean>(false);

const openDeclarationModal = () => {
  isDeclarationModalOpen.value = true;
}

const reportPost = (reportContent : string) => {
  // 실제로 리폿하는곳


}
</script>

<style scoped>
.q-page {
  max-width: 1000px;
  margin: 0 auto;
}

.left-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.right-section {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.info-section {
  margin-bottom: 0.5rem;
}

.row {
  display: flex;
  align-items: center;
  gap: 16px;
}

.q-pa-md {
  padding: 0.5rem;
}

.q-mr-md {
  margin-right: 0.5rem;
}

.q-mb-md {
  margin-bottom: 1rem;
}

.q-mb-md {
  margin-bottom: 16px;
}
</style>