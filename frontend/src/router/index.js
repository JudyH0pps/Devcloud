import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Profile from '../views/Profile.vue'
import DetailPage from '../views/Detail.vue'
import BoardPage from '../views/Board.vue'

Vue.use(VueRouter)

  const routes = [
  { 
    path: '*',
    name: 'NotFound',
    component: () => import('../components/error/NotFound.vue')
  },
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/question/write',
    name: 'WriteQuestion',
    component: () => import('../views/Write.vue')
  },
  {
    // path: '/detail/:qno',
    path: '/question/:question_id',
    name: 'Detail',
    component: DetailPage
  },
  {
    path: '/board',
    name: 'board',
    component: BoardPage
  },
  {
    path: '/account/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/ranking',
    name: 'Ranking',
    component: () => import('../views/Ranking.vue')
  },
  {
    path: '/question/:question_id/write',
    name: 'WriteAnswer',
    component: () => import('../views/WriteAnswer.vue')
  },
  {
    path: '/question/:question_id/edit',
    name: 'Edit',
    component: () => import('../views/Write.vue')
  },
  {
    path: '/oauth2/redirect',
    redirect: { name: 'Home' }
  },
  {
    path: '/question/:question_id/:answer_id/edit',
    name: 'EditAnswer',
    component: () => import('../views/WriteAnswer.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
