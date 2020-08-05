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
                .post('/api/comment', commentData)
                .then(({data}) => {
                    console.log(data)
                    context.commit('setComments', data)
                })
                .catch(err => console.log(err))
        }
    }
}