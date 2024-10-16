<template>
  <q-page class="q-pa-md row justify-center">
    <div class="col-4">
      <q-card>
        <q-card-section>
          <h4 class="text-center">사용자 목록</h4>
          <q-input
            filled
            borderless
            dense
            debounce="300"
            color="primary"
            v-model="searchUser"
            class="q-pa-md"
          >
            <template v-slot:append>
              <q-icon name="search" />
            </template>
          </q-input>
          <q-list bordered>
            <q-item
              v-for="(user, index) in userList"
              :key="index"
              clickable
              @click="getUser(user.memberId)"
              :class="{ 'text-negative': user.memberIsDelete }"
            >
              <q-item-section>
                <q-item-label>{{ user.memberNickname }}</q-item-label>
                <q-item-label caption>{{ user.memberEmail }}</q-item-label>
              </q-item-section>
              <q-item-section side>
                <q-icon name="chevron_right" />
              </q-item-section>
            </q-item>
          </q-list>
          <div class="q-pa-sm flex flex-center">
            <q-pagination
              v-model="currentPage"
              :max="5"
              max-pages="5"
              boundary-numbers
              class="q-mt-md"
            />
          </div>
        </q-card-section>
      </q-card>
    </div>
    <q-space class="custom-space" />
    <div class="col-6">
      <q-card>
        <q-card-section class="text-center">
          <h4>{{userDetailData.memberNickname}}</h4>
          <h6>{{userDetailData.memberEmail}}</h6>
          <div class="info-section q-mb-md">
            <div class="row q-col-gutter-ms q-mt-md">
              <div class="col">
                <span style="font-weight: bold">이름</span><br />
                <span class="col-2">{{userDetailData.memberName}}</span>
              </div>
              <q-separator vertical />
              <div class="col">
                <span style="font-weight: bold">가입 일자</span><br />
                <span class="col-3">{{userDetailData.memberCreatedAt}}</span>
              </div>
              <q-separator vertical />
              <div class="col">
                <span style="font-weight: bold">신고누적횟수</span><br />
                <span class="col-3">{{userDetailData.memberReportTotalCount}}</span>
              </div>
            </div>
          </div>
        </q-card-section>
      </q-card>

      <q-card class="q-mt-lg" style="min-height: 300px">
        <q-card-section class="flex justify-center">
          <q-btn
            color="primary"
            label="게시글"
            @click="getBoardByMemberId"
            class="q-mr-sm"
          />
          <q-btn
            color="primary"
            label="댓글"
            @click="getCommentByMemberId"
            class="q-mr-sm"
          />
          <q-btn color="primary" label="신고" @click="getReportByMemberId" />
        </q-card-section>

        <div class="q-pa-md">
          <q-table
            flat
            bordered
            title="Treats"
            :rows="rows"
            :columns="columns"
            row-key="name"
            hide-header
            hide-bottom
            @row-click="onRowClick"
          >
            <template v-slot:top>
              <h4 style="font-weight: bold">게시글</h4>
              <q-space />
              <q-input
                filled
                borderless
                dense
                debounce="300"
                color="primary"
                v-model="searchText"
              >
                <template v-slot:append>
                  <q-icon name="search" />
                </template>
              </q-input>
            </template>
          </q-table>
          <div class="flex justify-center">
            <q-pagination
              v-model="currentPage"
              :max="Math.ceil(rows.length / itemsPerPage)"
              boundary-numbers
              class="q-mt-md"
            />
          </div>
        </div>
      </q-card>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { UserData, UserListData } from '@/type/UserData'
import { getUserDataAll, getUserDataDetail } from '@/api/AuthRequiredApi'

const columns = [
  {
    name: 'title',
    required: true,
    label: 'title',
    align: 'left',
    field: row => row.name,
    format: val => `${val}`,
    sortable: true,
    width: '40%',
  },
  { name: 'author', label: 'author', field: 'carbs', width: '30%' },
  { name: 'date', label: 'date', field: 'protein', width: '30%' },
]

const rows = [
  { name: 'Lollipop', author: 'John Doe', date: '2024-01-01' },
  { name: 'Honeycomb', author: 'Jane Smith', date: '2024-01-02' },
  { name: 'Donut', author: 'Alice Johnson', date: '2024-01-03' },
  { name: 'KitKat', author: 'Bob Brown', date: '2024-01-04' },
  { name: 'Frozen Yogurt', author: 'Charlie Black', date: '2024-01-05' },
  { name: 'Ice Cream Sandwich', author: 'David White', date: '2024-01-06' },
  { name: 'Eclair', author: 'Eva Green', date: '2024-01-07' },
  { name: 'Cupcake', author: 'Frank Blue', date: '2024-01-08' },
  { name: 'Gingerbread', author: 'Grace Yellow', date: '2024-01-09' },
  { name: 'Jelly Bean', author: 'Henry Red', date: '2024-01-10' },
]

const searchUser = ref<string>('')
const searchText = ref<string>('')
const currentPage = ref<number>(1)
const itemsPerPage = 10
const userList = ref<UserListData[] | undefined>([])

const transformToUserList = (response: UserData): UserListData => {
  return {
    memberId: response.memberId,
    memberEmail: response.memberEmail,
    memberNickname: response.memberNickname,
    memberIsDelete: response.memberIsDelete,
  }
}

const getUserList = async () => {
  try {
    const response = await getUserDataAll()
    userList.value =
      response.data.body.memberResponseDtoList.map(transformToUserList)
  } catch (error) {
    console.log(error)
  }
}

const userDetailData = ref<UserData>({
  memberId: 0,
  memberEmail: '',
  memberNickname: '',
  memberName: '',
  memberCreatedAt: '',
  memberReportTotalCount: 0,
})

const transformToUser = (response: UserData): UserData => {
  return {
    memberId: response.memberId,
    memberEmail: response.memberEmail,
    memberNickname: response.memberNickname,
    memberName: response.memberName,
    memberCreatedAt: response.memberCreatedAt,
    memberReportTotalCount: 0,
  }
}

const getUser = async (memberId: number): UserData => {
  try {
    // 사용자 정보
    const response = await getUserDataDetail(memberId)
    // 신고한 누적 횟수

    console.log(response)
    userDetailData.value = transformToUser(response.data.body, 0)
    console.log(userDetailData.value)
  } catch (error) {
    console.log(error)
  }
}

const onRowClick = () => {
  // 클릭하면 해당 게시글 띄우기
  console.log('hi')
}

const getBoardByMemberId = () => {
  console.log('사용자의 게시글 가져오기')
}

const getCommentByMemberId = () => {
  console.log('사용자의 댓글 가져오기')
}

const getReportByMemberId = () => {
  console.log('사용자의 신고글 가져오기')
}

onMounted(async () => {
  await getUserList()
})
</script>

<style scoped>
q-card {
  padding: 0; /* 카드 내 여백 제거 */
}

q-card-section {
  padding: 0; /* 카드 섹션 내 여백 제거 */
}

.custom-space {
  flex-grow: 0.1 !important; /* 원하는 크기로 설정 */
}

.text-negative {
  color: red; /* 글자 색상을 빨간색으로 */
}
</style>
