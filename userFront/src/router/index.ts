import { createRouter, createWebHistory } from 'vue-router'
import BoardView from '@/views/BoardView.vue'
import SignUp from '@/views/SignUpView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
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
  ]
})

export default router
