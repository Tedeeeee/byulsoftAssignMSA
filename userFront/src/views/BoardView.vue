<template>
  <search-bar-component v-model="searchConditionForBoard" />
  <sort-type-component v-model="searchConditionForBoard" @sort="sortPostList"/>
  <reset-search-button @reset-search-condition="resetSearchBoard"/>
  {{ userStore().user}}
  <q-page padding>
    <div class="q-gutter-md">
      <post-card-component v-for="(post, idx) in boards" :key="idx" :post="post" @detail-post="detailPost" />
      <div class="q-pa-lg flex flex-center">
        <q-pagination
          v-model="currentPage"
          color="black"
          :max="totalPages || 1"
          :max-pages="7"
          :boundary-numbers="false"
          @update:model-value="handlePageChange"
        />
      </div>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { SearchBoard } from '@/type/SearchData'
import type { BoardListData } from '@/type/BoardData'
import { getBoardList } from '@/api/NoAuthRequiredApi'
import SearchBarComponent from '@/components/board/SearchBarComponent.vue'
import ResetSearchButton from '@/components/board/ResetSearchButtonComponent.vue'
import PostCardComponent from '@/components/board/PostCardComponent.vue'
import { userStore } from '@/stores/UserStore'
import SortTypeComponent from '@/components/board/SortTypeComponent.vue'

const router = useRouter();
const route = useRoute();

const currentPage = ref<number>(1);
const totalPages = ref<number>(1);
const boards = ref<BoardListData[]>([]);

const searchConditionForBoard = ref<SearchBoard>({
  searchType: route.query.searchType || '',
  searchText: route.query.searchText || '',
  sortOrder: route.query.sortOrder || '',
  sortType: route.query.sortType || '',
  pageNumber: parseInt(route.query.pageNumber) || currentPage.value,
})

const detailPost = (boardId: number): void => {
  router.push({name: 'BoardDetail', params: {boardId}});
}

// 검색
const sortPostList = async () => {
  searchConditionForBoard.value.pageNumber = 1;
  await fetchPosts();

  await router.push({
    name: 'Board',
    query: {
      searchType: searchConditionForBoard.value.searchType,
      searchText: searchConditionForBoard.value.searchText,
      sortOrder: searchConditionForBoard.value.sortOrder,
      sortType: searchConditionForBoard.value.sortType,
      pageNumber: 1,
    },
  });
}

// 검색 리셋
const resetSearchBoard = async () => {
  searchConditionForBoard.value = {
    searchType: '',
    searchText: '',
    sortOrder: '',
    sortType: '',
    pageNumber: 1,
  };

  await fetchPosts()
}

const handlePageChange = async (page: number) => {
  searchConditionForBoard.value.pageNumber = page;
  currentPage.value = page;

  await fetchPosts();

  await router.push({
    name: 'Board',
    query: {
      searchType: searchConditionForBoard.value.searchType,
      searchText: searchConditionForBoard.value.searchText,
      sortOrder: searchConditionForBoard.value.sortOrder,
      sortType: searchConditionForBoard.value.sortType,
      pageNumber: searchConditionForBoard.value.pageNumber,
    },
  });
};

const fetchPosts = async () => {
  const response = await getBoardList(searchConditionForBoard.value);
  boards.value = response.data.body.boards;
  totalPages.value = response.data.body.totalPages;
}

onMounted(async () => {
  currentPage.value = parseInt(route.query.pageNumber, 10) || 1;
  searchConditionForBoard.value.pageNumber = parseInt(route.query.pageNumber, 10) || 1;
  await fetchPosts();
})

</script>

<style scoped>
.q-page {
  max-width: 1000px;
  margin: 0 auto;
}
</style>