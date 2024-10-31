<template>
  <q-page padding>
    <q-form @submit.prevent="submitForm" class="q-gutter-md">
      <PostContentComponent v-model="postContents" />
      <q-btn type="submit" label="작성하기" color="primary" class="q-mt-md" />
    </q-form>
  </q-page>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useNotifications } from '@/common/CommonNotify'
import PostContentComponent from '@/components/PostContentComponent.vue'
import type { BoardSaveData } from '@/type/BoardData'
import { insertBoard } from '@/api/AuthRequiredApi'

const { negativeNotify, positiveNotify } = useNotifications()
const router = useRouter()

const postContents = ref<BoardSaveData>({
  boardTitle: '',
  boardContent: '',
  boardRegion: '',
  boardStars: [
    { boardStarType: 'DIFFICULTY', boardStarShortReview: '', boardStarRating: 0 },
    { boardStarType: 'STORY', boardStarShortReview: '', boardStarRating: 0 },
    { boardStarType: 'INTERIOR', boardStarShortReview: '', boardStarRating: 0 },
    { boardStarType: 'ACTIVITY', boardStarShortReview: '', boardStarRating: 0 },
    { boardStarType: 'HORROR', boardStarShortReview: '', boardStarRating: 0 }
  ]
})

const submitForm = async () => {
  for (let i = 0; i < postContents.value.boardStars.length; i++) {
    if (postContents.value.boardStars[i].boardStarRating === 0) {
      negativeNotify('별점을 입력해주세요')
      return
    }
  }

  try {
    const response = await insertBoard(postContents.value)
    positiveNotify('게시글이 등록되었습니다.')
    await router.push('/');
  } catch (error) {
  }
}
</script>

<style scoped>
</style>