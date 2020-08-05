import http from "@/util/http-common"
import router from '@/router'

export default {
    namespaced: true,
    state: {
        comments: []
    },
    mutations: {
        setComments(state, comments) {
            state.comments = comments
        }
    },
    actions: {
        postComment(context, commentData) {
            http
                .post('/api/comments', commentData)
                .then(({data}) => {
                    console.log(data)
                    context.dispatch('fetchComments', commentData.answer_id)
                })
                .catch(err => console.log(err))
        },
        fetchComments(context, commentData) {
            http
                .get('/api/comments', {params: {
                    answer_id: commentData.answer_id,
                }})
                .then(({data}) => {
                    console.log(data)
                    context.commit('setComments', data)
                })
                .catch(err => console.log(err))
        }
    }
}