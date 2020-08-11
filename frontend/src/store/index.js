import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import user from "@/store/modules/user.js";
import question from "@/store/modules/question.js";
import answer from "@/store/modules/answer.js";
import notification from "@/store/modules/notification.js";

export default new Vuex.Store({
  state: {
    testValid : true
  },
  mutations: {
      settestValid(state,payload){
        state.testValid = payload
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
