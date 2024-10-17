<template>
  {{contentsType}}
  <alert-modal-component label-message="닫기" message="검색 결과가 존재하지 않습니다" />
  <q-page class="q-pa-md row justify-center">
    <div class="col-4">
      <q-card>
        <q-card-section>
          <h4 class="text-center">사용자 목록</h4>
          <q-input
            filled
            borderless
            dense
            label="닉네임 검색"
            debounce="300"
            color="primary"
            v-model="searchUser"
            @keyup.enter="searchUserList(searchUser)"
            class="q-pa-md"
          >
            <template v-slot:append>
              <q-icon name="search" @click="searchUserList(searchUser)" />
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
              :max="userTotalPage"
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
      <q-card v-if="searchData.memberId !== 0">
        <q-card-section class="text-center">
          <h4>{{ userDetailData.memberNickname }}</h4>
          <h6>{{ userDetailData.memberEmail }}</h6>
          <div class="info-section q-mb-md">
            <div class="row q-col-gutter-ms q-mt-md">
              <div class="col">
                <span style="font-weight: bold">이름</span><br />
                <span class="col-2">{{ userDetailData.memberName }}</span>
              </div>
              <q-separator vertical />
              <div class="col">
                <span style="font-weight: bold">가입 일자</span><br />
                <span class="col-3">{{ userDetailData.memberCreatedAt }}</span>
              </div>
              <q-separator vertical />
              <div class="col">
                <span style="font-weight: bold">신고누적횟수</span><br />
                <span class="col-3">{{
                  userDetailData.memberReportTotalCount
                }}</span>
              </div>
            </div>
          </div>
        </q-card-section>
      </q-card>

      <q-card
        class="q-mt-lg"
        style="min-height: 300px"
        v-if="searchData.memberId !== 0"
      >
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
        <user-report-component
          v-if="contentsType === '신고'"
          v-model="searchData"
          :contents-type="contentsType"
          :report-list="reportList"
          :total-page="totalPage"
          :reporterMemberNickname="userDetailData.memberNickname"
        />
        <user-board-and-comment-component
          v-if="contentsType !== '신고'"
          v-model="searchData"
          :contents-type="contentsType"
          :board-list="boardList"
          :comment-list="commentList"
          :total-page="totalPage"
          @page-move="pageHandle"
          @boardPageView="boardDetail"
        />
      </q-card>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { UserData, UserListData } from '@/type/UserData'
import {
  callApi,
  getUserBoards, getUserByNickname, getUserComments,
  getUserDataAll,
  getUserReportCount, getUserReports
} from '@/api/AuthRequiredApi'
import type { SearchData } from '@/type/SearchData'
import UserBoardAndCommentComponent from '@/components/mainPage/UserBoardAndCommentComponent.vue'
import type { BoardData } from '@/type/BoardData'
import { ModalStore } from '@/stores/ModalStore'
import AlertModalComponent from '@/components/modal/AlertModalComponent.vue'
import type { CommentData } from '@/type/CommentData'
import { useRouter } from 'vue-router'
import UserReportComponent from '@/components/mainPage/UserReportComponent.vue'
import type { ReportData } from '@/type/ReportData'

const router = useRouter();
const currentPage = ref<number>(1)
const totalPage = ref<number>(1)
const userTotalPage = ref<number>(1)
const contentsType = ref<string>('')

const userList = ref<UserListData[] | undefined>([])
const userDetailData = ref<UserData>({
  memberId: 0,
  memberEmail: '',
  memberNickname: '',
  memberName: '',
  memberCreatedAt: '',
  memberReportTotalCount: 0,
  memberIsDelete: false,
})
const searchUser = ref<string>('')

const boardList = ref<BoardData>([])
const commentList = ref<CommentData>([])
const reportList = ref<ReportData>([])
const searchData = ref<SearchData>({
  searchType: '',
  searchText: '',
  startDate: '',
  endDate: '',
  pageNumber: 1,
  memberId: 0,
})

const resetSearchData = () => {
  searchData.value = {
    searchType: '',
    searchText: '',
    startDate: '',
    endDate: '',
    pageNumber: 1,
    memberId: 0,
  }

  contentsType.value = ''
}

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

const transformToUserList = (response: UserData): UserListData => {
  return {
    memberId: response.memberId,
    memberEmail: response.memberEmail,
    memberNickname: response.memberNickname,
    memberIsDelete: response.memberIsDelete,
  }
}

const transformToBoardList = (response: BoardData): BoardData => {
  return {
    boardId: response.boardId,
    memberNickname: response.memberNickname,
    boardTitle: response.boardTitle,
    boardCreatedAt: response.boardCreatedAt,
    boardIsDelete: response.boardIsDelete,
  }
}

const getUser = async (memberId: number): UserData => {
  try {
    resetSearchData();
    searchData.value.memberId = memberId

    const response = await callApi(`/adminService/admins/users/${memberId}`, {});

    userDetailData.value = transformToUser(response.data.body, 0)

    const reportResponse = await getUserReportCount(memberId)
    userDetailData.value.memberReportTotalCount = reportResponse.data.body;
  } catch (error) {
    console.log(error);
  }
}

const getUserList = async () => {
  try {
    const response = await getUserDataAll()
    userTotalPage.value = Math.ceil(response.data.body.totalMembers / 20  )
    userList.value = response.data.body.memberResponseDtoList.map(transformToUserList)
  } catch (error) {
    console.log(error)
  }
}

const searchUserList = async (memberNickname: string) : UserData => {
  try {
    const response = await getUserByNickname(memberNickname);
    console.log(response.data.body.memberResponseDtoList.length)

    if (response.data.body.memberResponseDtoList.length === 0) {
      ModalStore().openModal()
      return
    }
    userList.value = response.data.body.memberResponseDtoList;
  } catch (error) {
    console.log(error)
  }
}

const getBoardByMemberId = async () => {
  boardList.value = [];
  contentsType.value = '게시글'
  try {
    // 가져와서 데이터 넣기
    const response = await getUserBoards(searchData.value)
    totalPage.value = response.data.body.totalPages
    boardList.value = response.data.body.boards.map(transformToBoardList)
  } catch (error) {
    console.log(error)
  }
}

const getCommentByMemberId = async () => {
  commentList.value = [];
  contentsType.value = '댓글'
  try {
    const response = await getUserComments(searchData.value)
    console.log(response)
    commentList.value = response.data.body.commentResponseDtoList;
    console.log(commentList.value)
  } catch (error) {
    console.log(error)
  }
}

const getReportByMemberId = async () => {
  reportList.value = [];
  contentsType.value = '신고'
  try {
    const response = await getUserReports(searchData.value.memberId)
    console.log(response)
    reportList.value = response.data.body;
    console.log(reportList.value)
  } catch (error) {
    console.log(error)
  }
}

const boardDetail = async (boardId: number) => {
  console.log(boardId)
  await router.push({name: 'boardDetail', params: {boardId}});
}



const pageHandle = async () => {
  // 변경된 페이지로 인한 데이터 새롭게 전달
  try {
    const response = await getUserBoards(searchData.value)
    console.log(response)
    boardList.value = response.data.body.boards.map(transformToBoardList)
  } catch (error) {
    console.log(error)
  }
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
