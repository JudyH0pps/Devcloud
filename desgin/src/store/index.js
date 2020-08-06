import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import user from "@/store/modules/user.js";
import question from "@/store/modules/question.js";
import answer from "@/store/modules/answer.js";
import notification from "@/store/modules/notification.js";

export default new Vuex.Store({
  state: {
    keyword : "",
    searchBarinHeadbar: true,
  },
  mutations: {
    setKeyword(state, payload){
      state.keyword = payload
    },
    resetKeyword(state){
      state.keyword=""
    }
  },
  actions: {
  },
  modules: {
    user,
    question,
    answer,
    notification,
  },
})
