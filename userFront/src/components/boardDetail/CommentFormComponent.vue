<template>
  <div>
    <q-dialog v-model="isDialogOpen">
      <q-card>
        <q-card-section>
          <div class="text-h6">{{ modalMessage }}</div>
        </q-card-section>
        <q-card-actions align="center">
          <q-btn v-if="modalMessage === '수정하시겠습니까?'" flat label="수정하기" @click="closeModal(modalMessage)" color="primary" />
          <q-btn v-if="modalMessage === '삭제하시겠습니까?'" flat label="삭제하기" @click="closeModal(modalMessage)" color="primary" />
          <q-btn flat label="닫기" @click="closeModal" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
  <div class="comment-section q-mt-lg">
    <h5>댓글</h5>
    <div class="q-mt-md">
      <q-card v-for="(comment, idx) in comments" :key="idx" flat bordered class="q-pa-md comment-card">
        <div class="comment-header row items-center q-mb-md">
          <strong class="col">{{ comment.memberNickname }}</strong>
          <span>{{ comment.commentUpdatedAt }}</span>
          <div v-if="comment.memberNickname === nickname" class="comment-actions col-auto">
            <q-btn flat label="수정" @click="openEditComment(comment.commentId)" color="primary" class="q-mr-xs" />
            <q-btn flat label="삭제" @click="checkIfDelete(comment.commentId)" color="negative" class="q-mr-xs" />
          </div>
        </div>
        <q-separator />
        <div v-if="comment.isEdit">
          <q-input filled v-model="newUpdateComment" label="댓글을 수정하세요" type="textarea" rows="3" class="q-mb-md" />
          <q-btn label="수정하기" @click="showModal('수정하시겠습니까?', comment.commentId)" color="primary" />
        </div>
        <p v-if="!comment.isEdit" class="comment-text">{{ comment.commentContent }}</p>
      </q-card>
    </div>
    <q-card flat bordered class="q-pa-md">
      <q-input filled v-model="newComment" label="댓글을 작성해주세요" type="textarea" rows="3" class="q-mb-md" />
      <q-btn label="댓글 작성하기" @click="submitComment" color="primary" />
    </q-card>
  </div>
</template>
<script setup lang="ts">
import { ref, defineProps } from 'vue';
import { userStore } from '@/stores/UserStore'
import { useNotifications } from '@/common/CommonNotify';
import { CommentData } from '@/type/CommentData'

const { negativeNotify } = useNotifications();
const nickname = userStore().user.memberNickname;

const newComment = ref<string>('');
const newUpdateComment = ref<string>('');
const nowCommentId = ref<number>(0);

const modalMessage = ref<string>('');
const isDialogOpen = ref<boolean>(false);
const props = defineProps<{
  comments: CommentData[];
}>();

const emit = defineEmits<{
  (e: 'editComment', content: string, id: number): void;
  (e: 'deleteComment', id: number): void;
  (e: 'addComment', content: string): void;
}>();

const showModal = (message: string, commentId: number) => {
  nowCommentId.value = commentId;
  modalMessage.value = message;
  isDialogOpen.value = true;
};

const closeModal = (message: string) => {
  if (message === '수정하시겠습니까?') {
    editComment(newUpdateComment.value, nowCommentId.value);
  } else if (message === '삭제하시겠습니까?') {
    deleteComment(nowCommentId.value);
  }
  isDialogOpen.value = false;
};

const openEditComment = (commentId: number) => {
  props.comments.find(comment => {
    if (comment.commentId === commentId) {
      newUpdateComment.value = comment.commentContent;
      comment.isEdit = true;
    } else {
      comment.isEdit = false;
    }
  });
};

const checkIfDelete = (commentId: number) => {
  nowCommentId.value = commentId;
  showModal('삭제하시겠습니까?', nowCommentId.value);
};

/* 삭제를 실행 */
const deleteComment = (commentId: number) => {
  emit('deleteComment', commentId);
};

/* 수정하기를 누르면 실행 */
const editComment = (newUpdateComment: string, commentId: number) => {
  if (newUpdateComment.trim() === '') {
    negativeNotify('내용을 작성해주세요');
    return;
  }
  emit('editComment', newUpdateComment, commentId);
};

const submitComment = () => {
  if (newComment.value.trim()) {
    emit('addComment', newComment.value);
    newComment.value = '';
  }
};
</script>

<style scoped>
.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.comment-actions {
  display: flex;
  gap: 8px;
}

.comment-card {
  margin-top: 16px;
  margin-bottom: 16px;
}

.comment-text {
  white-space: pre-wrap;
  margin: 0px;
}
</style>