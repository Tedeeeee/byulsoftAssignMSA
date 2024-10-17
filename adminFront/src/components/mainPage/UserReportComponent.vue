<template>
  <report-modal-component :report-data="reportData" :reporter-member-nickname="reporterMemberNickname" />
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      title="Treats"
      :rows="reportList"
      :columns="reportColumns"
      row-key="boardId"
      hide-bottom
      @row-click="onRowClick"
    >
      <template v-slot:top>
        <h4 style="font-weight: bold">{{ contentsType }}</h4>
        <q-space />
        <div class="row q-gutter-sm q-mt-md justify-between">
          <div class="col">
            <q-input
              filled
              borderless
              dense
              readonly
              v-model="searchData.startDate"
              label="시작 날짜"
              class="q-mt-none"
            >
              <template v-slot:append>
                <q-icon name="event" />
              </template>
              <q-popup-proxy>
                <q-date v-model="searchData.startDate" mask="YYYY-MM-DD" />
              </q-popup-proxy>
            </q-input>
          </div>
          <div class="col">
            <q-input
              filled
              borderless
              dense
              readonly
              v-model="searchData.endDate"
              label="끝 날짜"
              class="q-mt-none"
            >
              <template v-slot:append>
                <q-icon name="event" />
              </template>
              <q-popup-proxy>
                <q-date
                  v-model="searchData.endDate"
                  :options="minimumDate"
                  minmask="YYYY-MM-DD"
                />
              </q-popup-proxy>
            </q-input>
          </div>
          <div class="col">
            <q-input
              filled
              borderless
              dense
              debounce="300"
              color="primary"
              v-model="searchData.searchText"
              label="검색"
            />
          </div>
          <div class="col-1">
            <div class="flex justify-center">
              <q-btn color="primary" label="검색" @click="searchReports" />
            </div>
          </div>
        </div>
      </template>
    </q-table>
    <div class="flex justify-center">
      <q-pagination
        v-model="searchData.pageNumber"
        :max="totalPage"
        boundary-numbers
        @update:model-value="handlePageChange"
        class="q-mt-md"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { type ReportData, ReportState, ReportType } from '@/type/ReportData'
import ReportModalComponent from '@/components/modal/ReportModalComponent.vue'
import { ref } from 'vue'
import { ModalStore } from '@/stores/ModalStore'

const searchData = defineModel()
const reportData = ref<ReportData>({
  reportId: 0,
  reportedMemberNickname: '',
  reportContent: '',
  reportType: '',
  reportTypeId: 0,
  reportCreatedAt: '',
  reportState: ''
});

defineProps<{
  contentsType: string
  reportList: ReportData
  totalPage: number
  reporterMemberNickname: string
}>()

const emit = defineEmits<{
  (e : 'pageMove'): void
  (e : 'boardPageView', boardId: number): void
}>();

const reportColumns = [
  { name: 'Report Type', field: rows => ReportType[rows.reportType], label: '신고 타입', align: 'center', width: '15%' },
  { name: 'Report Reason', field: rows => truncateText(rows.reportContent), label: '신고사유', align: 'center', width: '15%' },
  { name: 'Reported Member', field: rows => rows.reportedMemberNickname, label: '신고 당한 사람', align: 'center', width: '15%' },
  { name: 'Report Date', field: rows => rows.reportCreatedAt, label: '신고 일시', align: 'center', width: '25%' },
  { name: 'Report Status', field: rows => ReportState[rows.reportState], label: '처리 상황', align: 'center', width: '20%' },
];

const onRowClick = async (row, event) => {
  // 해당 정보 가져와서 모달에 정보 넣고 띄우기
  reportData.value = event;
  ModalStore().openReportModal()
}

const handlePageChange = async () => {
  emit('pageMove');
}

const searchReports = async () => {
  console.log("검색")
}

const truncateText = (text: string): string => {
  return text.length >= 20 ? text.slice(0, 20) + '...' : text
}

const minimumDate = (endDate: string) => {
  const startDateFormatted = searchData.value.startDate.replace(/-/g, '/')
  const endDateFormatted = endDate.replace(/-/g, '/')

  return endDateFormatted >= startDateFormatted
}
</script>

<style scoped>
</style>
