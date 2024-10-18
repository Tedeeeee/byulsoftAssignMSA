import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/views/SignUpView.vue'
import MainPage from '@/views/MainPageView.vue'
import Login from '@/views/LoginView.vue'
import BoardDetail from '@/views/BoardDetailView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'MainPage',
      component: MainPage
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
      path: '/:boardId',
      name: 'BoardDetail',
      component: BoardDetail,
      props: true,
    },
  ]
})

export default router
