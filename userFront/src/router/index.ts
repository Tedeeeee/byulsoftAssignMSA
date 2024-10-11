import { createRouter, createWebHistory } from 'vue-router'
import BoardView from '@/views/BoardView.vue'
import SignUp from '@/views/SignUpView.vue'
import Login from '@/views/LoginView.vue'
import PostDetail from '@/views/BoardDetailView.vue'
import UpdatePost from '@/views/PostEditView.vue'
import InsertPost from '@/views/PostCreateView.vue'
import MainHeader from '@/components/MainHeader.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/mainHeader',
      name: 'Header',
      component: MainHeader,
    },
    {
      path: '/',
      name: 'BoardView',
      component: BoardView
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
      name: 'PostDetail',
      component: PostDetail,
      props: true,
    },
  ]
})

export default router
