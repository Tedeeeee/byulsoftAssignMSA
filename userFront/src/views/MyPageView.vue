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
        <div class="text-h6">{{userStore().user.memberNickname}}</div>
        <div class="text-subtitle2">{{userStore().user.memberEmail}}</div>
      </q-card-section>
    </q-card>

    <!-- 아이콘 그리드 -->
    <q-card class="q-ma-md">
      <q-card-section>
        <q-btn-group flat class="q-gutter-md">
          <q-btn icon="edit" label="닉네임 변경" stack-label class="col" @click="showType = 'changeNickname'"/>
          <q-btn icon="lock" label="비밀번호 변경" stack-label class="col" @click="showType = 'changePassword'"/>
          <q-btn icon="description" label="내가 쓴 글" stack-label class="col" @click="showType = 'posts'"/>
          <q-btn icon="comment" label="내가 쓴 댓글" stack-label class="col" @click="showType = 'comments'"/>
          <q-btn icon="report_problem" label="신고 내역" stack-label class="col" @click="showType = 'reports'"/>
        </q-btn-group>
      </q-card-section>
    </q-card>



    <!-- 내가 쓴 글 또는 댓글 목록 -->
    <q-card class="q-ma-md">
      <q-card-section>
        <div v-if="showType === 'changeNickname'" class="text-subtitle1 text-center">닉네임 변경</div>
        <div v-else-if="showType === 'changePassword'" class="text-subtitle1 text-center">비밀번호 변경</div>
        <div v-else-if="showType === 'posts'" class="text-subtitle1 text-center">내가 쓴 글</div>
        <div v-else-if="showType === 'comments'" class="text-subtitle1 text-center">내가 쓴 댓글</div>
        <div v-else-if="showType === 'reports'" class="text-subtitle1 text-center">신고 내역</div>

        <q-card v-if="showType === 'changeNickname'" class="q-ma-md" style="max-width: 300px; width: 100%; margin: 0 auto;">
          <q-card-section class="text-center">
            <q-input v-model="newNickname" label="새 닉네임" />
            <q-btn label="닉네임 변경" @click="changeNickname" color="primary" class="q-mt-md" />
          </q-card-section>
        </q-card>

        <q-card v-if="showType === 'changePassword'" class="q-ma-md" style="max-width: 300px; width: 100%; margin: 0 auto;">
          <q-card-section>
            <q-input v-model="newPassword" type="password" label="기존 비밀번호" />
            <q-input v-model="newPassword" type="password" label="새 비밀번호" />
            <q-input v-model="confirmPassword" type="password" label="새 비밀번호 확인" />
            <q-btn label="비밀번호 변경" @click="changePassword" color="primary" class="q-mt-md" />
          </q-card-section>
        </q-card>

        <!-- 글 목록 -->
        <q-list v-if="showType === 'posts'" bordered class="q-mt-sm">
          <q-item class="border-bottom">
            <q-item-section style="flex: 6;" class="text-center">
              <q-item-label class="font-weight-bold">제목 내용</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 3;" class="text-center">
              <q-item-label class="font-weight-bold">작성 날짜</q-item-label>
            </q-item-section>
          </q-item>

          <q-item
            v-for="(post, index) in posts"
            :key="index"
            clickable
            class="border-bottom"
          >
            <q-item-section style="flex: 6;">
              <q-item-label>{{ post.title }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label>{{ post.date }}</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon name="chevron_right" />
            </q-item-section>
          </q-item>
        </q-list>

        <!-- 댓글 목록 -->
        <q-list v-if="showType === 'comments'" bordered class="q-mt-sm">
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
            class="border-bottom"
          >
            <q-item-section style="flex: 6;">
              <q-item-label>{{ comment.content }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label>{{ comment.date }}</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon name="chevron_right" />
            </q-item-section>
          </q-item>
        </q-list>

        <!-- 신고 목록 -->
        <q-list v-if="showType === 'reports'" bordered class="q-mt-sm">
          <q-item class="border-bottom">
            <q-item-section style="flex: 2;" class="text-center">
              <q-item-label class="font-weight-bold">신고 상태</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 6;" class="text-center">
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
              <q-item-label>{{ report.status }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 6;" class="text-center">
              <q-item-label>{{ report.reason }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 3;" class="text-center">
              <q-item-label>{{ report.date }}</q-item-label>
            </q-item-section>
            <q-item-section style="flex: 2;" class="text-center">
              <q-btn label="취소" @click="cancelReport(index)" color="negative" />
            </q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { userStore } from '@/stores/UserStore.ts'
import { ref } from 'vue'

const showType = ref<string>('posts');
const posts = [
  { title: '첫 번째 글 제목이 겁나게 길면 어떻게 할꺼야????? 이렇게 길어지면 니가 어떻게표현할껀데', date: '2024-10-10 14:10' },
  { title: '두 번째 글 제목', date: '2024-10-11 15:10' },
  { title: '세 번째 글 제목', date: '2024-10-12 16:10' },
]
const comments = [
  { content: '첫 번째 댓글 내용', date: '2024-10-10 17:10' },
  { content: '두 번째 댓글 내용', date: '2024-10-11 18:10' },
  { content: '세 번째 댓글 내용', date: '2024-10-12 19:10' },
]
const reports = [
  { status: '처리중', reason: '부적절한 댓글', date: '2024-10-12' },
  { status: '완료', reason: '스팸글', date: '2024-10-10' },
  { status: '대기중', reason: '욕설 사용', date: '2024-10-09' },
];
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