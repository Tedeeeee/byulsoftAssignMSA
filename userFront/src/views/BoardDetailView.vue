<template>
  <div>
    <q-dialog v-model="isDialogOpen">
      <q-card>
        <q-card-section>
          <div class="text-h6">{{ modalMessage }}</div>
        </q-card-section>
        <q-card-actions align="center">
          <q-btn
            v-if="modalMessage === '수정하시겠습니까?'"
            flat
            label="수정하기"
            @click="closeModal"
            :to="`/updatePost/${boardId}`"
            color="primary"
          />
          <q-btn v-if="modalMessage === '삭제하시겠습니까?'" flat label="삭제하기" @click="deletePost(boardId)" color="primary" />
          <q-btn flat label="닫기" @click="closeModal" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
  <q-page padding>
    <div class="q-gutter-md" v-if="postHeadData">
      <div class="post-card">
        <board-detail-head-contents-component :postHeadData="postHeadData" />
      </div>
      <!-- 여기서 nickname을 확인하고 컴포넌트를 띄워주면 된다     -->
      <board-edit-delete-actions-component
        v-if="postHeadData.memberNickname === userStore().user.memberNickname"
        @update-modal="showModal"
        @delete-modal="showModal"
      />
      <div v-for="(star, idx) in boardStars" :key="idx" class="post-card">
        <review-star-component :star="star" :image="images[idx]" />
        <q-separator />
      </div>
      <div class="review-summary q-mt-lg">
        <q-card flat class="q-pa-md review-summary-card">
          <q-img :src="totalReview" alt="사진" style="width: 170px; height: 170px" />
          <p class="text-center">
            {{ postHeadData.boardContent }}
          </p>
        </q-card>
        <q-separator />
      </div>

      <comment-form-component :comments="comments" @delete-comment="deletedComment" @add-comment="addComment" @edit-comment="editComment" />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import totalReview from '@/assets/총평.png';
import horrorImage from '@/assets/공포도.png';
import difficultyImage from '@/assets/난이도.png';
import storyImage from '@/assets/스토리.png';
import interiorImage from '@/assets/인테리어.png';
import activityImage from '@/assets/활동성.png';
import { CommentData } from '@/type/CommentData'
import type { BoardData, BoardHeaderData, BoardStarData } from '@/type/BoardData'
import { useRouter } from 'vue-router';
import { userStore } from '@/stores/UserStore'
import { useNotifications } from '@/common/CommonNotify';
import { getBoardById, getCommentById } from '@/api/NoAuthRequiredApi'
import { deleteBoard, insertComment, updateComment, deleteComment } from '@/api/AuthRequiredApi'
import BoardDetailHeadContentsComponent from '@/components/boardDetail/BoardDetailHeadContentsComponent.vue'
import ReviewStarComponent from '@/components/boardDetail/ReviewStarComponent.vue'
import BoardEditDeleteActionsComponent from '@/components/boardDetail/BoardEditDeleteActionsComponent.vue'
import CommentFormComponent from '@/components/boardDetail/CommentFormComponent.vue'

const { negativeNotify, positiveNotify } = useNotifications();
const router = useRouter();
const images = [difficultyImage, storyImage, interiorImage, activityImage, horrorImage];
const props = defineProps<{
  boardId: string;
}>();
const boardId = parseInt(props.boardId);
const postHeadData = ref<BoardHeaderData | undefined>({
  boardId: 0,
  memberNickname: '',
  boardRegion: '',
  boardTitle: '',
  boardCreatedAt: '',
});
const boardStars = ref<BoardStarData[] | undefined>([]);
const comments = ref<CommentData[] | undefined>([]);
const isDialogOpen = ref<boolean>(false);
const modalMessage = ref<string>('');

const transformToPostHeadData = (responseData: BoardData): BoardHeaderData => {
  return {
    boardId: responseData.boardId,
    boardTitle: responseData.boardTitle,
    boardContent: responseData.boardContent,
    boardRegion: responseData.boardRegion,
    memberNickname: responseData.memberNickname,
    boardCreatedAt: responseData.boardCreatedAt,
  };
};

const transformToBoardStar = (responseData: BoardStarData): Omit<BoardStarData, 'boardId' | 'boardStarId'> => {
  return {
    boardStarType: responseData.boardStarType,
    boardStarShortReview: responseData.boardStarShortReview,
    boardStarRating: responseData.boardStarRating,
  };
};

const transformToComment = (responseData: CommentData) : CommentData => {
  return {
    commentId: responseData.commentId,
    memberId: responseData.memberId,
    memberNickname: responseData.memberNickname,
    commentUpdatedAt: responseData.commentUpdatedAt,
    commentContent: responseData.commentContent,
    showReplyForm: false,
    isEdit: false,
  };
};

/* 게시글 삭제 여부 확인 모달*/
const showModal = (message: string) => {
  modalMessage.value = message;
  isDialogOpen.value = true;
};

const closeModal = () => {
  isDialogOpen.value = false;
};

/* 게시글 삭제 */
const deletePost = async (boardId: number) => {
  try {
    const response = await deleteBoard(boardId);
    console.log(response)
    positiveNotify(response.data.message);
    await router.push('/');
  } catch (error) {
    negativeNotify(error.message);
  }
};

/* 댓글 추가 */
const addComment = async (content: string) => {
  if (content.trim() === '') {
    negativeNotify('글을 입력해주세요');
    return;
  }

  try {
    const response = await insertComment({
      memberId: userStore().user.memberId,
      boardId: boardId,
      commentContent: content,
    });

    positiveNotify(response.data.message);
    comments.value = response.data.body.map(transformToComment);
  } catch (error) {
    if (error.status == 401) {
      negativeNotify('로그인이 필요한 서비스입니다');
      await router.push('/login');
    }
  }
};

/* 댓글 수정 */
const editComment = async (content: string, id: number) => {
  try {
    const response = await updateComment({
      commentContent: content,
      commentId: id,
      boardId: boardId,
      memberId: userStore().user.memberId
    });

    console.log(response)
    positiveNotify(response.data.message);
    if (response.data.body) {
      comments.value = response.data.body.map(transformToComment);
    }
  } catch (error) {
    negativeNotify(error);
  }

};

/* 댓글 삭제 */
const deletedComment = async (commentId: number) => {
  console.log(commentId)
  try {
    const response = await deleteComment(commentId);
    positiveNotify(response.data.message);

    /*삭제 후 해당 보드의 댓글 다시 로드*/
    const responseComment = await getCommentById(boardId);
    comments.value = responseComment.data.body.map(transformToComment);
  } catch (error) {
    negativeNotify(error.message);
  }
};

const fetchContentDetails = async () => {
  const response = await getBoardById(boardId);
  postHeadData.value = transformToPostHeadData(response.data.body);
  boardStars.value = response.data.body.boardStars.map(transformToBoardStar);

  // 댓글 따로 가져오기
  const commentResponse = await getCommentById(boardId)
  if (commentResponse.data.body) {
    comments.value = commentResponse.data.body.map(transformToComment);
  }
};

onMounted(() => {
  fetchContentDetails();
});
</script>

<style scoped>
.q-page {
  max-width: 1000px;
  margin: 0 auto;
}

.post-card {
  transition: background-color 0.3s;
}

.post-card:hover {
  background-color: #f5f5f5;
}

.q-pa-md {
  padding: 0.5rem;
}
.q-mt-lg {
  margin-top: 32px;
}

.review-summary {
  text-align: center;
}

.review-summary-card {
  margin-top: 16px;
  margin-bottom: 16px;
}

.text-center {
  white-space: pre-wrap;
}
</style>