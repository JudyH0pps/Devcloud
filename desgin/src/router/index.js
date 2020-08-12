import Vue from 'vue'
import VueRouter from 'vue-router'
import Profile from '../views/Profile.vue'
import Home from '../views/Home.vue'

Vue.use(VueRouter)
  const routes = [
  {
    path: '*',
    redirect: '/404'
  },
  {
    path: '/404',
    component: () => import('@/views/PageNotFound.vue')
  },
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/write',
    name: 'Write',
    component: () => import('../views/Write.vue')
  },
  {
    path: '/detail/:question_id/write',
    name: 'WriteAnswer',
    component: () => import('../components/WriteAnswer.vue')
  },
  {
    path: '/profile/:user_id',
    name: 'Profile',
    component: Profile
    //component: () => import('../views/Profile.vue')
  },
  {
    path: '/search/:search_keyword',
    name: 'Search',
    component: () => import('../views/Search.vue')
  },
  {
    path: '/ranking',
    name: 'Ranking',
    component: () => import('../views/Ranking.vue')
  },
  {
    path: '/oauth2/redirect',
    redirect: { name: 'Home' }
  },
  {
    path: '/detail/:question_id',
    name: 'Detail',
    component: () => import('../views/Detail.vue')
  },
  {
    path: '/detail/:question_id/edit/:answer_id',
    name: 'EditAnswer',
    component: () => import('../components/WriteAnswer.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
