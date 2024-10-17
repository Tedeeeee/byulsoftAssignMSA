<template>
  <q-dialog v-model="ModalStore().reportModalState">
    <q-card class="bg-white text-black">
      <q-card-section class="q-pa-md">
        <div class="text-h6 text-center q-mb-md">신고 처리</div>
        <q-list>
          <q-item class="q-py-sm">
            <q-item-section>
              <q-item-label>신고당한 사람:</q-item-label>
              <q-item-label class="text-h6">{{ reportData.reportedMemberNickname }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item class="q-py-sm">
            <q-item-section>
              <q-item-label>신고한 사람:</q-item-label>
              <q-item-label class="text-h6">{{ reporterMemberNickname }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item class="q-py-sm">
            <q-item-section>
              <q-item-label>신고 사유:</q-item-label>
              <q-item-label class="text-h6">{{ reportData.reportContent }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item class="q-py-sm">
            <q-item-section>
              <q-item-label>신고 타입:</q-item-label>
              <q-item-label class="text-h6">{{ ReportType[reportData.reportType] }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item class="q-py-sm">
            <q-item-section>
              <q-item-label>신고 날짜:</q-item-label>
              <q-item-label class="text-h6">{{ reportData.reportCreatedAt }}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item class="q-py-sm">
            <q-item-section>
              <q-item-label>신고 상태:</q-item-label>
              <q-item-label class="text-h6">{{ ReportState[reportData.reportState] }}</q-item-label>
            </q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
      <q-card-actions align="center">
        <q-btn label="처리" @click="handleReportProcessing" color="negative" />
        <q-btn label="반려" @click="handleReportCanceling" color="warning" />
        <q-btn label="닫기" @click="ModalStore().closeReportModal()" color="positive" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>
<script setup lang="ts">
import { ModalStore } from '@/stores/ModalStore'
import { type ReportData, ReportState, ReportType } from '@/type/ReportData'

defineProps<{
  reportData: ReportData
  reporterMemberNickname: string
}>()

const handleReportProcessing = () => {
  console.log('신고 진행')
}

const handleReportCanceling = () => {
  console.log('취소')
}

</script>

<style scoped>
.text-h6 {
  font-weight: bold;
}
</style>
