import Vue from 'vue'
import VueRouter from 'vue-router'

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
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/write',
    name: 'Write',
    component: () => import('../views/Write.vue')
  },
  {
    path: '/writeanswer',
    name: 'WriteAnswer',
    component: () => import('../components/WriteAnswer.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('../views/Profile.vue')
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
    path: '/detail',
    name: 'Detail',
    component: () => import('../views/Detail.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
