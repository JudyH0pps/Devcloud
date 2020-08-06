import http from "@/util/http-common"
import router from '@/router'

export default {
	namespaced: true,
	state: {
		answer : {},
        answers: [],
    },
	mutations: {
        setAnswers(state, answers) {
            state.answers = answers
        }
    },
	actions: {
        fetchAnswers({ commit }, question_id) {
					http
						.get('/api/answers', {
							params: {
								question_id: question_id
							}
						})
						.then(({data}) => {
							// 응답 데이터 확인 => 상태 변경
							commit('setAnswers', data)
						})
						.catch(err => console.log(err))
		},
		fetchAnswersById(context,payload){
					http
						.get('/api/answers',{
							params:{
								user_id : payload
							}
						})
						.then(({data}) => {
							context.commit('setAnswers',data)
							console.log(data);
						})
						.catch((err) => {
							console.log(err);
							alert('유저ID로 답변 조회중 오류 발생');
						})
		},
		postAnswer(context, answerData) {
			http
				.post('/api/answers', answerData)
				.then(() => {
					router.push({name: 'Detail', params: {"question_id": answerData.question_id}})
				})
				.catch(err => console.log(err))
		},
		editAnswer(context, answerData) {
			http
				.put('/api/answers', answerData)
				.then(() => {
					router.push({name: 'Detail', params: {"question_id": answerData.question_id}})
				})
				.catch(err => console.log(err))
		},
		deleteAnswer(context, answerData) {
			http
				.delete('/api/answers', {params: {id: answerData.answer_id}})
				.then(() => context.dispatch('fetchAnswers', answerData.question_id))
				.catch(err => console.log(err))
		}
    },
	getters: {},
}