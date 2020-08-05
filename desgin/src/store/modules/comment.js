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
        fetchComments(context, answer_id) {
            http
                .get('/api/comments', {params: {
                    answer_id: answer_id,
                }})
                .then(({data}) => {
                    console.log(data)
                    context.commit('setComments', data)
                })
                .catch(err => console.log(err))
        },
        editComment(context, commentData) {
            http
                .put('/api/comments', commentData)
                .then(({data}) => {
                    console.log(data)
                    context.dispatch('fetchComments', commentData.answer_id)
                })
                .catch(err => console.log(err))
        },
        deleteComment(context, commentData) {
            http
                .delete('/api/comments', commentData.comment_id)
                .then(() => {
                    context.dispatch('fetchComments', commentData.answer_id)
                })
                .catch(err => console.log(err))
        }
    }
}