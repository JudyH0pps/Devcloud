import http from "@/util/http-common"
import router from '@/router'
import question from "./question";

export default {
    namespaced: true,
    state: {
        like: {},
    },
    mutations: {
        // request한 값을 state.like에 삽입 payload: 매개변수
        setLikeToQuestion(state, payload) {
            state.like = payload;
        }
    },
    actions: {
        // question like count
        // quesiont_id && user_id 있으면 좋아요 취소
        // quesiont_id && !user_id  좋아요
        postQuestionLike(context, likeData) {
            http
                .post('/api/liketoquestion'), {
                    params: {
                        "question_id": question_id,
                        "user_id": user_id,
                    }
                }
                .then(({data}) => {
                    
                    // 좋아요 상태 변화....
                    commit('setLikeToQuestion', data);
                })
                .catch(err => console.log(err.response))
        },
        postAnswerLike(context, likeData) {
            http
                .post('/api/liketoanswer'), {
                    params: {
                        "question_id": question_id,
                        "user_id": user_id,
                    }
                }
                .then(({data}) => {
                    
                    // 좋아요 상태 변화....
                    commit('setLikeToQuestion', data);
                })
                .catch(err => console.log(err.response))
        },
    }
}