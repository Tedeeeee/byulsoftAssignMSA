<template>
  <custom-modal/>
  <search-bar v-model="SearchConditionForBoard" />
  <sort-type v-model="SearchConditionForBoard" @sort="sortPostList"/>
  <reset-search-button @reset-search-condition="resetSearchBoard"/>
  <q-page padding>
    <div class="q-gutter-md">
      <post-card v-for="(post, idx) in Boards" :key="idx" :post="post" @detail-post="detailPost" />
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
import type { BoardData } from '@/type/BoardData'
import SearchBar from '@/components/board/SearchBarComponent.vue'
import CustomModal from '@/components/modal/AlertModalComponent.vue'
import SortType from '@/components/board/SortTypeComponent.vue'
import ResetSearchButton from '@/components/board/ResetSearchButtonComponent.vue'
import PostCard from '@/components/board/PostCardComponent.vue'

const router = useRouter();
const route = useRoute();

const currentPage = ref<number>(1);
const totalPages = ref<number>(1);
const Boards = ref<BoardData[]>([]);

const SearchConditionForBoard = ref<SearchBoard>({
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
const sortPostList = () => {

}

// 검색 리셋
const resetSearchBoard = () => {
  SearchConditionForBoard.value = {
    searchType: '',
    searchText: '',
    sortOrder: '',
    sortType: '',
    pageNumber: 1,
  };


}

const fetchPosts = async () => {

}

onMounted(async () => {
  currentPage.value = parseInt(route.query.pageNumber, 10) || 1;
  searchBoard.value.pageNumber = parseInt(route.query.pageNumber, 10) || 1;
  await fetchPosts();
})

</script>

<style scoped></style>