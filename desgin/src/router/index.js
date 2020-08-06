import Vue from 'vue'
import VueRouter from 'vue-router'
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
    path: '/board',
    name: 'Board',
    component: () => import('../views/Board.vue')
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
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
