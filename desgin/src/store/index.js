import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import user from "@/store/modules/user.js";
import question from "@/store/modules/question.js";
import answer from "@/store/modules/answer.js";
import notification from "@/store/modules/notification.js";
import tech from "@/store/modules/tech.js"
import comment from "@/store/modules/comment.js"
import tag from "@/store/modules/tag.js"


export default new Vuex.Store({
  state: {
    testValid : true, //프로필 변경할수있는 상태인지 체크할 변수
    searchBarinHeadbar: true,
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
    tech,
    comment,
    tag,
  },
})
