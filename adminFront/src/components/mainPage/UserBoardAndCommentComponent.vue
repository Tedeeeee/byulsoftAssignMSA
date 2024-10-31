<template>
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      :rows="contentsType === '게시글' ? boardList : commentList"
      :columns="contentsType === '게시글' ? boardColumns : commentColumns"
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
              @keyup.enter="search"
              label="검색"
            />
          </div>
          <div class="col-1">
            <div class="flex justify-center">
              <q-btn color="primary" label="검색" @click="search" />
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
import type { BoardData } from '@/type/BoardData'
import type { CommentData } from '@/type/CommentData'

const searchData = defineModel()

const props = defineProps<{
  contentsType: string
  boardList: BoardData
  commentList: CommentData
  totalPage: number
}>()

const emit = defineEmits<{
  (e : 'pageMove'): void
  (e : 'boardPageView', boardId: number): void
  (e : 'searchBoard')
  (e : 'searchComment')
}>();

const minimumDate = (endDate: string) => {
  const startDateFormatted = searchData.value.startDate.replace(/-/g, '/')
  const endDateFormatted = endDate.replace(/-/g, '/')

  return endDateFormatted >= startDateFormatted
}

const onRowClick = async (row, event) => {
  emit('boardPageView', event.boardId)
}

const search = () => {
  if (props.contentsType === '게시글') {
    emit('searchBoard')
    return
  }
  emit('searchComment')
}

const handlePageChange = async () => {
  emit('pageMove');
}

const boardColumns = [
  { name: '상태', field: rows => rows.boardIsDelete ? '삭제' : '삭제 안됨', label: '상태', align: 'center', width: '10%'},
  { name: '제목', field: rows => truncateText(rows.boardTitle), label: '제목', align: 'center', width: '60%'},
  { name: '작성 일자', field: rows => rows.boardCreatedAt, label: '작성 일자', align: 'center', width: '30%' },
]

const commentColumns = [
  { name: '상태', field: rows => rows.commentIsDelete ? '삭제' : '삭제 안됨', label: '상태', align: 'center', width: '10%'},
  { name: '제목', field: rows => truncateText(rows.commentContent), label: '제목', align: 'center', width: '60%'},
  { name: '작성 일자', field: rows => rows.commentCreatedAt, label: '작성 일자', align: 'center', width: '30%' },
]

const truncateText = (text: string): string => {
  return text.length >= 20 ? text.slice(0, 20) + '...' : text
}

</script>

<style scoped>
</style>
