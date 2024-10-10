<template>
  <q-page padding>
    <q-form v-if="postContents" @submit.prevent="submitForm" class="q-gutter-md">
      <post-content-component v-model="postContents" />
      <q-btn type="submitForm" label="수정하기" color="primary" class="button-container q-mt-md" />
    </q-form>
  </q-page>
</template>

<script setup lang="ts">
import type { BoardData, BoardUpdateData } from '@/type/BoardData'
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useNotifications } from '@/common/CommonNotify';
import PostContentComponent from '@/components/PostContentComponent.vue'
import { getBoardById } from '@/api/NoAuthRequiredApi'
import { updateBoard } from '@/api/AuthRequiredApi'
import { userStore } from '@/stores/UserStore'

const { positiveNotify, negativeNotify } = useNotifications();
const router = useRouter();
const props = defineProps<{
  boardId: string;
}>();
const boardId = parseInt(props.boardId);
const postContents = ref<BoardUpdateData>({
  boardId: boardId,
  memberId: userStore.use.memberId,
  boardRegion: '',
  boardTitle: '',
  boardContent: '',
  boardStars: [],
});

const transformToPost = (serverData: BoardData) => {
  return {
    boardId: boardId,
    memberId: userStore.user.memberId,
    boardTitle: serverData.boardTitle,
    boardContent: serverData.boardContent,
    boardRegion: serverData.boardRegion,
    boardStars: serverData.boardStars,
  };
};

/* boardId를 통해 데이터를 다시 받아서 셋팅을 한다 */
const fetchContentDetails = async () => {
  const response = await getBoardById(boardId);
  postContents.value = transformToPost(response.data.body);
};

const submitForm = async () => {
  try {
    const response = await updateBoard(postContents.value);
    console.log(response)

    //positiveNotify(response.message);
    //await router.push(`/${boardId}`);
  } catch (error) {
    console.log(error)
    negativeNotify(error.message);
  }
};

onMounted(async () => {
  await fetchContentDetails();
});
</script>

<style scoped>
.q-page {
  max-width: 700px;
  margin: 0 auto;
}

.button-container {
  justify-content: center;
}
</style>