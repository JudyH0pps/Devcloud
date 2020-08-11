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
        getQuestonLikeCount({commit}, question_id) {
            http
                // 'api/question_id' get 요청
                .get('/api/liketoquestion'), {
                    // body parameter에 question_id를 실어서 보낸다.
                    params: {
                        "question_id": question_id
                    }
                }
                // 요청 이후 동작
                .then(({data}) => {
                    commit('setLikeToQustion', data)
                })
                // 요청시 에러발생시 동작
                .catch(err => console.log(err.response))
        },
        postQuestionLike(context, question_id) {
            http
                .post('/api/liketoquestion'), {
                    params: {
                        "question_id": question_id,

                    }
                }
        },
        // answer like count
        getAnswerLikeCount(context, answer_id){
            http
                .get('api/likeytoanswer'), {
                    // body parameter에 answer_id를 실어서 보낸다.
                    params: {
                        "answer_id": answer_id
                    }
                }
                .then(({data}) => {
                    context.commit('setLikeToAnswer', data)
                })
                .catch(err => console.log(err.response))
        }
    }
}