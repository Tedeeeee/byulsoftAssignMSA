import { createRouter, createWebHistory } from 'vue-router'
import Board from '@/views/BoardView.vue'
import SignUp from '@/views/SignUpView.vue'
import Login from '@/views/LoginView.vue'
import BoardDetail from '@/views/BoardDetailView.vue'
import UpdatePost from '@/views/PostEditView.vue'
import InsertPost from '@/views/PostCreateView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Board',
      component: Board
    },
    {
      path: '/signUp',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/insertPost',
      name: 'InsertPost',
      component: InsertPost,
    },
    {
      path: '/updatePost/:boardId',
      name: 'UpdatePost',
      component: UpdatePost,
      props: true,
    },
    {
      path: '/:boardId',
      name: 'BoardDetail',
      component: BoardDetail,
      props: true,
    },
  ]
})

export default router
