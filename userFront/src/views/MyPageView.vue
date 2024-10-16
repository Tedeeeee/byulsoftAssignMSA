<template>
  <q-page padding>
    <q-toolbar>
      <q-toolbar-title class="main-text-center">
        마이페이지
      </q-toolbar-title>
    </q-toolbar>

    <!-- 사용자 정보 카드 -->
    <q-card class="q-ma-md text-center">

      <q-card-section>
        <div class="text-h6" style="font-weight: bold">{{userStore().user.memberNickname}}</div>
        <div class="text-subtitle2">{{userStore().user.memberEmail}}</div>
      </q-card-section>
    </q-card>

    <!-- 아이콘 그리드 -->
    <q-card class="q-ma-md">
      <q-card-section>
        <q-btn-group flat class="q-gutter-md">
          <q-btn icon="edit" label="닉네임 변경" stack-label class="col" @click="showType = 'nickname'"/>
          <q-btn icon="lock" label="비밀번호 변경" stack-label class="col" @click="showType = 'password'"/>
          <q-btn icon="description" label="내가 쓴 글" stack-label class="col" @click="getMyBoards"/>
          <q-btn icon="comment" label="내가 쓴 댓글" stack-label class="col" @click="getMyComments"/>
          <q-btn icon="report_problem" label="신고 내역" stack-label class="col" @click="getMyReports"/>
        </q-btn-group>
      </q-card-section>
    </q-card>

    <!-- 내가 쓴 글 또는 댓글 목록 -->
    <q-card v-if="showType !== ''" class="q-ma-md">
      <q-card-section>
        <div v-if="showType === 'nickname'" class="text-subtitle1 text-center">닉네임 변경</div>
        <div v-else-if="showType === 'password'" class="text-subtitle1 text-center">비밀번호 변경</div>
        <div v-else-if="showType === 'board'" class="text-subtitle1 text-center">내가 쓴 글</div>
        <div v-else-if="showType === 'comment'" class="text-subtitle1 text-center">내가 쓴 댓글</div>
        <div v-else-if="showType === 'report'" class="text-subtitle1 text-center">신고 내역</div>

        <q-card v-if="showType === 'nickname'" class="q-ma-md" style="max-width: 350px; width: 100%; margin: 0 auto;">
          <q-card-section class="text-center">
            <q-input v-model="changeNicknameData.newNickname"
                     filled
                     ref="nicknameInput"
                     label="새 닉네임"
                     maxlength="10"
                     outlined
                     lazy-rules
                     :rules="nicknameRules"
                     :disable="changeNicknameData.confirmNickname"
                     autocomplete="off"
            >
              <template v-slot:append>
                <q-btn label="중복 체크" @click="checkNicknameAvailability" color="secondary" :disable="changeNicknameData.confirmNickname"/>
              </template>
            </q-input>
            <q-btn label="닉네임 변경" @click="changeUserNickname" color="primary" class="q-mt-md" />
          </q-card-section>
        </q-card>

        <q-card v-if="showType === 'password'" class="q-ma-md" style="max-width: 300px; width: 100%; margin: 0 auto;">
          <q-card-section class="text-center" v-if="!changePasswordData.checkOriginPassword">
            <q-input v-model="changePasswordData.originPassword"
                     filled
                     ref="passwordInput"
                     type="password"
                     label="기존 비밀번호"
                     outlined
                     :disable="changePasswordData.confirmPassword"
                     autocomplete="off"
                     @keyup.enter="checkMyPassword"
            />
            <q-btn label="비밀번호 확인" @click="checkMyPassword" color="primary" class="q-mt-md" />
          </q-card-section>
          <q-card-section v-if="changePasswordData.checkOriginPassword">
            <q-input
              filled
              v-model="changePasswordData.newPassword"
              label="새 비밀번호"
              type="password"
              outlined
              lazy-rules
              :rules="checkPassword"
              autocomplete="off"
              class="q-mb-md"
            />
            <q-input
              filled
              v-model="changePasswordData.doubleCheckPassword"
              label="새 비밀번호 재확인"
              type="password"
              outlined
              lazy-rules
              :rules="checkConfirmPassword"
              autocomplete="off"
              class="q-mb-md"
              @keyup.enter="changePassword"
            />
            <div class="text-center">
              <q-btn label="비밀번호 변경" @click="changePassword" color="primary" class="q-mt-xs" />
            </div>
          </q-card-section>
        </q-card>

        <!-- 글 목록 -->
        <q-list v-if="showType === 'board'" bordered class="q-mt-sm">
          <q-item class="border-bottom">
            <q-item-section style="flex: 6;" class="text-center">
              <q-item-label class="font-weight-bold">제목 내용</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 3;" class="text-center">
              <q-item-label class="font-weight-bold">작성 날짜</q-item-label>
            </q-item-section>
          </q-item>

          <q-item
            v-for="(post, index) in boards"
            :key="index"
            clickable
            @click="moveBoard(post.boardId)"
            class="border-bottom"
          >
            <q-item-section style="flex: 6;">
              <q-item-label>{{ post.boardTitle }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label>{{ post.boardCreatedAt }}</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon name="chevron_right" />
            </q-item-section>
          </q-item>
        </q-list>

        <!-- 댓글 목록 -->
        <q-list v-if="showType === 'comment'" bordered class="q-mt-sm">
          <q-item class="border-bottom">
            <q-item-section style="flex: 6;" class="text-center">
              <q-item-label class="font-weight-bold">댓글 내용</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 3;" class="text-center">
              <q-item-label class="font-weight-bold">작성 날짜</q-item-label>
            </q-item-section>
          </q-item>

          <q-item
            v-for="(comment, index) in comments"
            :key="index"
            clickable
            @click="moveBoard(comment.boardId)"
            class="border-bottom"
          >
            <q-item-section style="flex: 6;">
              <q-item-label>{{ comment.commentContent }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label>{{ comment.commentCreatedAt }}</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon name="chevron_right" />
            </q-item-section>
          </q-item>
        </q-list>

        <!-- 신고 목록 -->
        <q-list v-if="showType === 'report'" bordered class="q-mt-sm">
          <q-item class="border-bottom">
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label class="font-weight-bold">신고 상태</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label class="font-weight-bold">신고 대상</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 5;" class="text-center">
              <q-item-label class="font-weight-bold">신고 사유</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 3;" class="text-center">
              <q-item-label class="font-weight-bold">신고 날짜</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label class="font-weight-bold">취소 여부</q-item-label>
            </q-item-section>
          </q-item>

          <q-item v-for="(report, index) in reports" :key="index" class="border-bottom">
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label>{{ ReportState[report.reportState] }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label>{{ report.reported }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 5;" class="text-center">
              <q-item-label>{{ report.reportContent }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 3;" class="text-center">
              <q-item-label>{{ report.reportDate }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-btn v-if="report.reportState === 'RECEIVED'" label="취소" @click="cancelReport(report.reportId)" color="negative" />
              <q-btn v-else label="취소불가" color="red" :disable="true"/>
            </q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { accessToken, userStore } from '@/stores/UserStore.ts'
import { computed, ref } from 'vue'
import type { CommentData, MyCommentList } from '@/type/CommentData'
import type { BoardData, BoardListData } from '@/type/BoardData'
import type { MyReportData } from '@/type/ReportData'
import {
  cancleReport,
  changeNickname,
  checkOriginPassword,
  getMyBoard,
  getMyComment,
  getMyReport, logout, setNewPassword
} from '@/api/AuthRequiredApi'
import { useRouter } from 'vue-router'
import { ReportState } from '@/type/UtilsData'
import { useNotifications } from '@/common/CommonNotify'
import { checkNickname } from '@/api/NoAuthRequiredApi'

const {positiveNotify, negativeNotify} = useNotifications();
const router = useRouter();
const showType = ref<string>('nickname');
const boards = ref<BoardListData>({
  boardId: 0,
  boardTitle: '',
  boardCreatedAt: '',
})

const transformToBoardData = (responseData: BoardData): BoardListData => {
  return {
    boardId: responseData.boardId,
    boardTitle: responseData.boardTitle,
    boardCreatedAt: responseData.boardCreatedAt,
  };
};

const comments = ref<MyCommentList>({
  boardId: 0,
  commentContent: '',
  commentCreatedAt: ''
});

const transformToCommentData = (responseData: CommentData): MyCommentList => {
  return {
    boardId: responseData.boardId,
    commentContent: responseData.commentContent,
    commentCreatedAt: responseData.commentCreatedAt
  };
};

const reports = ref<MyReportData>({
  reportId: 0,
  reportState: '',
  reported: '',
  reportContent: '',
  reportCreatedAt: '',
})

const transformToReportData = (responseData: MyReportData): MyReportData => {
  return {
    reportId:  responseData.reportId,
    reportState: responseData.reportState,
    reported: responseData.reportedMemberNickname,
    reportContent: responseData.reportContent,
    reportDate: responseData.reportDate,
  };
};

const changePasswordData = ref({
  originPassword: '',
  checkOriginPassword: false,
  newPassword: '',
  doubleCheckPassword: '',
  confirmPassword: false,
})

const changeNicknameData = ref({
  newNickname: '',
  confirmNickname: false,
})

const checkMyPassword = async () => {
  try {
    const response = await checkOriginPassword(changePasswordData.value.originPassword);
    changePasswordData.value.checkOriginPassword = true;
    positiveNotify(response.data.message)
  } catch (error) {
    console.log(error)
    negativeNotify(error.response.data.message)
  }
}

const checkPassword = [
  (val) => (val && val.length > 0) || '비밀번호를 입력해주세요',
  (val) =>
    /^(?=.*[A-Z])(?=.*[!@#$%^*+=-]).{6,}$/.test(val) ||
    '대문자와 지정된 특수문자를 최소 하나씩 포함하고, 6글자 이상이어야 합니다'
]

const checkConfirmPassword = [
  (val) => (val && val.length > 0) || '비밀번호를 입력해주세요',
  (val) => changePasswordData.value.newPassword === val || '비밀번호가 일치하지 않습니다'
]

const isPasswordValid = computed(() => {

  const isPasswordValid = checkPassword.every((rule) => {
    const result = rule(changePasswordData.value.newPassword);
    console.log('isPasswordValid' + result)
    return typeof result === 'boolean' ? result : false;
  });

  const isConfirmPasswordValid = checkConfirmPassword.every((rule) => {
    const result = rule(changePasswordData.value.doubleCheckPassword);
    console.log('isConfirmPasswordValid' + result)
    return typeof result === 'boolean' ? result : false;
  });

  return isPasswordValid && isConfirmPasswordValid;
});

const changePassword = async () =>{
  console.log(isPasswordValid.value)
  if (!isPasswordValid.value) {
    negativeNotify("비밀번호를 정확히 입력해주세요");
    return;
  }

  try {
    const response = await setNewPassword(changePasswordData.value.newPassword);
    positiveNotify(response.data.message)
    await logout();
    await localStorage.removeItem(accessToken)
    await router.push('/login');
  } catch (error) {
    negativeNotify(error.response.data.message)
  }
}

const nicknameRules = [
  (val) => (val && val.length >= 3) || '3글자 이상의 닉네임을 입력해주세요',
  (val) => /^[a-zA-Z0-9가-힝]+$/.test(val) || '닉네임에는 특수문자와 공백을 사용할 수 없습니다.',
  (val) => (changeNicknameData.value.newNickname || !val ? true : '중복체크를 완료해주세요')
]

const checkNicknameAvailability = async () => {
  try {
    const response = await checkNickname(changeNicknameData.value.newNickname);
    positiveNotify(response.data.message)
    changeNicknameData.value.confirmNickname = true;
  } catch (error) {
    console.log(error)
    negativeNotify(error.response.data.message)
  }
}

const changeUserNickname = async () => {
  try {
    const response = await changeNickname(changeNicknameData.value.newNickname);
    positiveNotify(response.data.message)
    userStore().setUserNickname(changeNicknameData.value.newNickname);
    await router.push('/');
  } catch (error) {
    console.log(error)
    negativeNotify(error.response.data.message)
  }
}

const moveBoard = (boardId : number) => {
  router.push({name: 'BoardDetail', params: {boardId}});
}

const getMyBoards = async () => {
  const response = await getMyBoard();
  boards.value = response.data.body.map(transformToBoardData)

  showType.value = 'board'
}

const getMyComments = async () => {
  const response = await getMyComment();
  comments.value = response.data.body.map(transformToCommentData)

  showType.value = 'comment'
}

const getMyReports = async () => {
  const response = await getMyReport();
  reports.value = response.data.body.map(transformToReportData)

  showType.value = 'report'
}

const cancelReport = async (reportId: number) => {
  try {
    const response = await cancleReport(reportId);
    positiveNotify(response.data.message);

    await getMyReports();
  } catch (error) {
    negativeNotify(error.response.data.message)
  }
}
</script>

<style scoped>
.main-text-center {
  text-align: center;
  font-weight: bold;
  font-size: 50px;
}

.text-center {
  text-align: center;
  font-weight: bold;
}

.border-bottom {
  border-bottom: 1px solid #e0e0e0;
}

.border-bottom:last-child {
  border-bottom: none;
}
</style>